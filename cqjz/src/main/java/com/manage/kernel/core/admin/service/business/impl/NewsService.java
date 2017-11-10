package com.manage.kernel.core.admin.service.business.impl;

import com.manage.base.database.enums.NewsStatus;
import com.manage.base.database.enums.NewsType;
import static com.manage.base.database.enums.NewsType.fromType;
import com.manage.base.database.enums.TopicStatus;
import com.manage.base.exception.NewsNotFoundException;
import com.manage.base.exception.NewsNotImageException;
import com.manage.base.exception.NewsTopicNotFoundException;
import com.manage.base.exception.NewsTypeNotSupportException;
import com.manage.base.exception.NotFoundException;
import com.manage.base.exception.PrivilegeDeniedException;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.page.TreeNode;
import com.manage.base.supplier.page.TreeNodeNews;
import com.manage.base.utils.CoreUtil;
import com.manage.base.utils.StringUtil;
import com.manage.cache.CacheManager;
import com.manage.kernel.core.admin.service.activiti.IActFlowService;
import com.manage.kernel.core.model.dto.NewsDto;
import com.manage.kernel.core.model.dto.NewsTopicDto;
import com.manage.kernel.core.model.parser.NewsParser;
import com.manage.kernel.core.admin.service.business.INewsService;
import com.manage.kernel.core.model.parser.NewsTopicParser;
import com.manage.kernel.jpa.entity.News;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.jpa.entity.NewsTopic;
import com.manage.kernel.jpa.repository.NewsRepo;
import com.manage.kernel.jpa.repository.NewsTopicRepo;
import com.manage.kernel.spring.comm.Messages;
import com.manage.kernel.spring.comm.SessionHelper;
import java.util.HashMap;
import java.util.Map;
import javax.transaction.NotSupportedException;
import static org.activiti.engine.impl.util.json.XMLTokener.entity;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 * Created by bert on 17-8-25.
 */
@Service
public class NewsService implements INewsService {

    private static final String CACHE_NEWS_TOPICS_TREE = "cache_news_topics_tree";
    private static final String CACHE_NEWS_TOPICS_MAP = "cache_news_topics_map";

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private NewsTopicRepo newsTopicRepo;

    @Autowired
    private IActFlowService actFlowService;

    @Autowired
    private CacheManager<String, Object> cacheManager;

    @Override
    @Transactional
    public NewsDto submitNews(NewsDto newsDto) {

        News news = saveOrUpdateNews(newsDto);
        news.setStatus(NewsStatus.SUBMIT);
        newsRepo.save(news);
        actFlowService.actFlowCommit(news);
        return NewsParser.toDto(news);
    }

    @Override
    @Transactional
    public NewsDto saveNews(NewsDto newsDto) {
        return NewsParser.toDto(saveOrUpdateNews(newsDto));
    }

    private News saveOrUpdateNews(NewsDto newsDto) {
        News news;
        if (newsDto.getId() != null) {
            news = newsRepo.findOne(newsDto.getId());
            if (news == null) {
                throw new NewsNotFoundException();
            }
            if (!news.getStatus().canEdit()) {
                throw new PrivilegeDeniedException();
            }
            setNewsInfo(news, newsDto);
            news.setUpdatedAt(LocalDateTime.now());
            news.setUpdatedUser(SessionHelper.user());
        } else {
            news = new News();
            news.setNumber(CoreUtil.nextRandomID());
            setNewsInfo(news, newsDto);
            news.setHits(0);
            news.setCreatedAt(LocalDateTime.now());
            news.setCreatedUser(SessionHelper.user());
        }
        return newsRepo.save(news);
    }

    @Override
    @Transactional
    public NewsDto detail(String number) {
        News news = newsRepo.findByNumber(number);
        if (news == null) {
            return null;
        }
        return NewsParser.toDto(news);
    }

    @Override
    @Transactional
    public void drop(String number) {
        News news = newsRepo.findByNumber(number);
        if (news == null) {
            throw new NewsNotFoundException();
        }

        AdUser user = SessionHelper.user();
        if (StringUtil.notEquals(news.getCreatedBy(), user.getId())) {
            throw new PrivilegeDeniedException();
        }

        if (!news.getStatus().canDrop()) {
            throw new PrivilegeDeniedException();
        }

        news.setStatus(NewsStatus.DELETE);
        news.setUpdatedAt(LocalDateTime.now());
        news.setUpdatedUser(user);
        newsRepo.save(news);
    }

    @Override
    @Transactional
    public PageResult pageList(PageQuery page, NewsDto query) {
        Page<News> jpaPage = newsRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("createdBy"), SessionHelper.user().getId()));
            if (StringUtil.isNotBlank(query.getTitle())) {
                list.add(cb.like(root.get("title"), "%" + query.getTitle() + "%"));
            }
            if (StringUtil.isNotNull(query.getType())) {
                list.add(cb.equal(root.get("type"), query.getType()));
            }
            if (StringUtil.isNotNull(query.getStatus())) {
                list.add(cb.equal(root.get("status"), query.getStatus()));
            } else {
                list.add(cb.notEqual(root.get("status"), NewsStatus.DELETE));
            }
            if (StringUtil.isNotNull(query.getCreatedAt())) {
                list.add(cb.greaterThanOrEqualTo(root.get("createdAt").as(LocalDateTime.class), query.getCreatedAt()));
            }
            if (StringUtil.isNotNull(query.getCreatedAtEnd())) {
                list.add(cb.lessThanOrEqualTo(root.get("createdAt").as(LocalDateTime.class), query.getCreatedAtEnd()));
            }

            return cb.and(list.toArray(new Predicate[0]));
        }, page.sortPageDefault("id"));

        PageResult<NewsDto> pageResult = new PageResult<>();
        pageResult.setTotal(jpaPage.getTotalElements());

        List<NewsDto> newsDtos = new ArrayList<>();
        for (News news : jpaPage.getContent()) {
            NewsDto newsDto = NewsParser.toDto(news);
            newsDto.setTopic(news.getTopic());
            newsDto.setTopicName(typeTopicName(news.getTopic()));
            newsDto.setType(news.getType());
            newsDto.setTypeName(typeTopicName(news.getType()));
            newsDtos.add(newsDto);
        }
        pageResult.setRows(newsDtos);
        return pageResult;
    }

    private void setNewsInfo(News news, NewsDto newsDto) {
        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());

        TreeNodeNews node = typeTopicNode(newsDto.getType());
        if (node == null || node.getPid() == null) {
            throw new NewsTypeNotSupportException();
        }
        news.setTopic(Integer.valueOf(node.getPid().toString()));
        news.setType(newsDto.getType());
        if (hasImage(newsDto.getType())) {
            if (newsDto.getImageId() == null) {
                throw new NewsNotImageException();
            }
            news.setImageId(newsDto.getImageId());
        } else {
            news.setImageId(null);
        }
        news.setSource(newsDto.getSource());
        news.setStatus(NewsStatus.DRAFT);
    }

    private boolean hasImage(int type) {
        if (type <= 50) {
            NewsType newsType = NewsType.fromType(type);
            if (newsType != null) {
                return newsType.hasImage();
            }
        } else {
            NewsTopic topic = newsTopicRepo.findOne(type);
            if (topic != null) {
                return topic.getHasImage();
            }
        }
        return false;
    }

    @Override
    @Transactional
    public List<NewsDto> newestNews(AdUser user) {
        PageQuery page = new PageQuery();
        page.setPageNumber(1);
        page.setPageSize(5);
        page.setSortField("publishTime");
        page.setSortDirection(PageQuery.ORDER_DESC);
        Page<News> newses = newsRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("status"), NewsStatus.PASS));
            return cb.and(list.toArray(new Predicate[0]));
        }, page.sortPage());

        NewsDto newsDto;
        List<NewsDto> newsDtos = new ArrayList<>();
        for (News news : newses.getContent()) {
            newsDto = NewsParser.toDto(news);
            newsDto.setCreatedByOrgan(news.getCreatedUser().getOrganName());
            newsDtos.add(newsDto);
        }
        return newsDtos;
    }

    @Override
    @Transactional
    public NewsTopicDto topicDetail(Integer code) {
        NewsTopic topic = newsTopicRepo.findOne(code);
        if (topic == null) {
            throw new NewsTopicNotFoundException();
        }
        return NewsTopicParser.toDto(topic);
    }

    @Override
    @Transactional
    public PageResult topicPageList(PageQuery page, NewsTopicDto query) {
        Page<NewsTopic> jpaPage = newsTopicRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("level"), 1));
            if (StringUtil.isNotBlank(query.getName())) {
                list.add(cb.like(root.get("name"), "%" + query.getName() + "%"));
            }
            if (StringUtil.isNotNull(query.getStatus())) {
                list.add(cb.equal(root.get("status"), query.getStatus()));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, page.sortPageDefault("code"));
        PageResult<NewsTopicDto> pageResult = new PageResult<>();
        pageResult.setTotal(jpaPage.getTotalElements());
        pageResult.setRows(NewsTopicParser.toDtoList(jpaPage.getContent()));
        return pageResult;
    }

    @Override
    @Transactional
    public NewsTopicDto saveNewsTopic(NewsTopicDto topicDto) {
        NewsTopic topic;
        if (StringUtil.isEmpty(topicDto.getCode())) {
            topic = new NewsTopic();
            topic.setStatus(TopicStatus.ENABLED);
            topic.setCreatedAt(LocalDateTime.now());
            topic.setCreatedUser(SessionHelper.user());
        } else {
            topic = newsTopicRepo.findOne(topicDto.getCode());
            if (topic == null) {
                throw new NewsTopicNotFoundException();
            }
            topic.setStatus(topicDto.getStatus());
            topic.setUpdatedAt(LocalDateTime.now());
            topic.setUpdatedUser(SessionHelper.user());
        }
        topic.setLevel(1);
        topic.setName(topicDto.getName());
        topic.setDescription(topicDto.getDescription());

        newsTopicRepo.save(topic);
        cacheNewsTopics();
        return NewsTopicParser.toDto(topic);
    }

    @Override
    @Transactional
    public NewsTopicDto saveNewsTopicLines(NewsTopicDto topicDto) {
        NewsTopic topic = newsTopicRepo.findOne(topicDto.getCode());
        if (topic == null) {
            throw new NotFoundException();//TODO
        }
        NewsTopic topicLine;
        int sequence = 0;
        List<NewsTopic> topicLines = new ArrayList<>();
        for (NewsTopicDto lineDto : topicDto.getTopicLines()) {
            if (lineDto.getCode() == null) {
                topicLine = new NewsTopic();
                topicLine.setCreatedAt(LocalDateTime.now());
                topicLine.setCreatedUser(SessionHelper.user());
                topicLine.setStatus(TopicStatus.ENABLED);
                topicLine.setParent(topic);
            } else {
                topicLine = newsTopicRepo.findTopicsByCode(lineDto.getCode(), topicDto.getCode());
                if (topicLine == null) {
                    throw new NotFoundException();//TODO
                }
                topicLine.setUpdatedAt(LocalDateTime.now());
                topicLine.setStatus(lineDto.getStatus());
                topicLine.setUpdatedUser(SessionHelper.user());
            }
            topicLine.setLevel(2);
            topicLine.setName(lineDto.getName());
            topicLine.setSequence(++sequence);
            topicLine.setHasImage(lineDto.getHasImage());
            topicLine.setDescription(lineDto.getDescription());
            topicLines.add(newsTopicRepo.save(topicLine));
        }
        topic.setTopicLines(topicLines);
        cacheNewsTopics();
        return NewsTopicParser.toDto(topic);
    }

    @Override
    @Transactional
    public List<NewsTopicDto> rootNewsTopics() {
        List<NewsTopic> topics = newsTopicRepo.queryRootTopics(TopicStatus.ENABLED);
        return NewsTopicParser.toDtoList(topics);
    }

    @Override
    @Transactional
    public List<TreeNodeNews> newsTopicTree() {
        return (List<TreeNodeNews>) cacheManager.get(CACHE_NEWS_TOPICS_TREE);
    }

    @Override
    @Transactional
    public void cacheNewsTopics() {
        List<TreeNodeNews> trees = new ArrayList<>();
        TreeNodeNews tree = new TreeNodeNews();
        tree.setId(NewsType.TOPIC.getConstant());
        tree.setName(Messages.get(NewsType.TOPIC.messageKey()));
        trees.add(tree);
        for (NewsType type : NewsType.getTypeList()) {
            tree = new TreeNodeNews();
            tree.setPid(NewsType.TOPIC.getConstant());
            tree.setId(type.getConstant());
            tree.setName(Messages.get(type.messageKey()));
            tree.setHasImage(type.hasImage());
            trees.add(tree);
        }
        List<NewsTopic> topics = newsTopicRepo.queryRootTopics(TopicStatus.ENABLED);
        for (NewsTopic topic : topics) {
            List<TreeNodeNews> tempTrees = new ArrayList<>();
            for (NewsTopic line : topic.getTopicLines()) {
                tree = new TreeNodeNews();
                tree.setId(line.getCode());
                tree.setName(line.getName());
                tree.setPid(line.getParentCode());
                tree.setHasImage(line.getHasImage());
                tree.setEnabled(line.getStatus().isEnable());
                tempTrees.add(tree);
            }
            if (CollectionUtils.isEmpty(tempTrees)) {
                continue;
            }
            tree = new TreeNodeNews();
            tree.setId(topic.getCode());
            tree.setName(topic.getName());
            trees.add(tree);
            trees.addAll(tempTrees);
        }

        Map<Integer, TreeNodeNews> topicMap = new HashMap<>();
        for (TreeNodeNews<Integer> node : trees) {
            topicMap.put(node.getId(), node);
        }
        cacheManager.put(CACHE_NEWS_TOPICS_MAP, topicMap);
        cacheManager.put(CACHE_NEWS_TOPICS_TREE, trees);
    }

    private TreeNodeNews typeTopicNode(Integer code) {
        return ((Map<Integer, TreeNodeNews>) cacheManager.get(CACHE_NEWS_TOPICS_MAP)).get(code);
    }

    @Override
    public String typeTopicName(Integer code) {
        TreeNode node = typeTopicNode(code);
        if (node == null) {
            return null;
        }
        return node.getName();
    }
}
