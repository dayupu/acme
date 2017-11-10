package com.manage.kernel.core.admin.view.comm;

import com.manage.base.act.support.ActBusiness;
import com.manage.base.database.enums.ApproveRole;
import com.manage.base.database.enums.FlowSource;
import com.manage.base.database.enums.NewsStatus;
import com.manage.base.database.enums.NewsType;
import com.manage.base.database.enums.TopicStatus;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.supplier.page.SelectOption;
import com.manage.base.supplier.page.TreeNode;
import com.manage.base.supplier.page.TreeNodeNews;
import com.manage.kernel.core.admin.service.business.INewsService;
import com.manage.kernel.core.admin.service.system.IOrganService;
import com.manage.kernel.core.model.dto.NewsTopicDto;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.comm.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/10/6.
 */
@RestController
@RequestMapping("/admin/constant")
public class ConstantController {

    @Autowired
    private IOrganService organService;

    @Autowired
    private INewsService newsService;

    @InboundLog
    @GetMapping("/newsTypeTree")
    public List<TreeNodeNews> newsTypeTree() {
        List<TreeNodeNews> newsTrees = new ArrayList<>();
        for (TreeNodeNews node : newsService.newsTopicTree()) {
            if (!node.isEnabled()) {
                continue;
            }
            newsTrees.add(node);
        }
        return newsTrees;
    }

    @InboundLog
    @GetMapping("/newsStatus")
    public ResponseInfo newsStatus() {
        ResponseInfo response = new ResponseInfo();
        List<SelectOption> options = new ArrayList<>();
        SelectOption<Integer, String> option;
        for (NewsStatus type : NewsStatus.values()) {
            if (type == NewsStatus.DELETE) {
                continue;
            }
            option = new SelectOption<>();
            option.setKey(type.getConstant());
            option.setValue(Messages.get(type));
            options.add(option);
        }
        response.wrapSuccess(options);
        return response;
    }

    @InboundLog
    @GetMapping("/simpleStatus")
    public ResponseInfo simpleStatus() {
        ResponseInfo response = new ResponseInfo();
        List<SelectOption> options = new ArrayList<>();
        SelectOption<Integer, String> option;
        for (TopicStatus status : TopicStatus.values()) {
            option = new SelectOption<>();
            option.setKey(status.getConstant());
            option.setValue(Messages.get(status));
            options.add(option);
        }
        response.wrapSuccess(options);
        return response;
    }

    @InboundLog
    @GetMapping("/actType")
    public List<TreeNode> actType() {
        TreeNode treeNode;
        List<TreeNode> treeNodes = new ArrayList<>();
        for (FlowSource source : FlowSource.values()) {
            switch (source) {
            case NEWS:
                String code = source.getCode();
                treeNode = new TreeNode();
                treeNode.setId(code);
                treeNode.setName(Messages.get(source.messageKey()));
                treeNodes.add(treeNode);
                // load news topic and type
                List<TreeNode> newsTrees = new ArrayList<>();
                for (TreeNodeNews node : newsService.newsTopicTree()) {
                    treeNode = new TreeNode();
                    treeNode.setName(node.getName());
                    if (node.getPid() == null) {
                        treeNode.setPid(code);
                    } else {
                        treeNode.setPid(ActBusiness.join(code, node.getPid()));
                    }
                    treeNode.setId(ActBusiness.join(treeNode.getPid(), node.getId()));
                    newsTrees.add(treeNode);
                }
                treeNodes.addAll(newsTrees);
                break;
            default:
                break;
            }
        }
        return treeNodes;
    }

    @InboundLog
    @GetMapping("/organs")
    public List<TreeNode> organs() {
        return organService.organTree();
    }

    @InboundLog
    @GetMapping("/approveRole")
    public ResponseInfo approveRole() {
        ResponseInfo response = new ResponseInfo();
        List<SelectOption> options = new ArrayList<>();
        SelectOption<Integer, String> option;
        for (ApproveRole role : ApproveRole.values()) {
            option = new SelectOption<>();
            option.setKey(role.getConstant());
            option.setValue(Messages.get(role));
            options.add(option);
        }
        response.wrapSuccess(options);
        return response;
    }

    @InboundLog
    @GetMapping("/rootTopics")
    public ResponseInfo newsTopics() {
        ResponseInfo response = new ResponseInfo();
        List<SelectOption> options = new ArrayList<>();
        SelectOption<Integer, String> option;
        List<NewsTopicDto> topicDtos = newsService.rootNewsTopics();
        for (NewsTopicDto topic : topicDtos) {
            option = new SelectOption<>();
            option.setKey(topic.getCode());
            option.setValue(topic.getName());
            options.add(option);
        }
        response.wrapSuccess(options);
        return response;
    }

}
