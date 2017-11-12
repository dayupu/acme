package com.manage.kernel.core.admin.service.business.impl;

import com.manage.base.exception.CoreException;
import com.manage.base.supplier.msgs.MessageErrors;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.utils.StringHandler;
import com.manage.kernel.core.model.dto.WatchDto;
import com.manage.kernel.core.model.parser.WatchParser;
import com.manage.kernel.core.admin.service.business.IWatchService;
import com.manage.kernel.jpa.entity.JzWatch;
import com.manage.kernel.jpa.repository.JzWatchRepo;
import com.manage.kernel.spring.comm.SessionHelper;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Created by bert on 17-10-13.
 */
@Service
public class WatchService implements IWatchService {

    @Autowired
    private JzWatchRepo watchRepo;

    @Override
    @Transactional
    public void saveWatch(WatchDto watchDto) {

        LocalDate watchTimeEnd = watchDto.getWatchTime();
        int differ = 0;
        if (watchDto.getWatchTimeEnd() != null) {
            watchTimeEnd = watchDto.getWatchTimeEnd();
            if (watchTimeEnd.compareTo(watchDto.getWatchTime()) < 0) {
                throw new CoreException(MessageErrors.WATCH_TIME_ERROR);
            }
            Days days = Days.daysBetween(watchDto.getWatchTime(), watchTimeEnd);
            differ = days.getDays();
            if (differ > 30) {
                throw new CoreException(MessageErrors.WATCH_TIME_RANGE_INVALID);
            }
        }

        List<JzWatch> watches = watchRepo.findByWatchTime(watchDto.getWatchTime(), watchTimeEnd);
        for (JzWatch watch : watches) {
            watchRepo.delete(watch);
        }

        int day = 0;
        JzWatch watch;
        do {
            watch = new JzWatch();
            watch.setWatchTime(watchDto.getWatchTime().plusDays(day++));
            watch.setPhone(watchDto.getPhone());
            watch.setLeader(watchDto.getLeader());
            watch.setCaptain(watchDto.getCaptain());
            watch.setWorker(watchDto.getWorker());
            watch.setCreatedAt(LocalDateTime.now());
            watch.setCreatedUser(SessionHelper.user());
            watchRepo.save(watch);
            differ--;
        } while (differ >= 0);
    }

    @Override
    @Transactional
    public PageResult pageList(PageQuery page, WatchDto query) {
        Page<JzWatch> results = watchRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (StringHandler.isNotNull(query.getWatchTime())) {
                list.add(cb.greaterThanOrEqualTo(root.get("watchTime"), query.getWatchTime()));
            }
            if (StringHandler.isNotNull(query.getWatchTimeEnd())) {
                list.add(cb.lessThanOrEqualTo(root.get("watchTime"), query.getWatchTimeEnd()));
            }

            if (StringHandler.isNotBlank(query.getQueryName())) {
                List<Predicate> orlist = new ArrayList<>();
                orlist.add(cb.like(root.get("leader"), "%" + query.getQueryName() + "%"));
                orlist.add(cb.like(root.get("worker"), "%" + query.getQueryName() + "%"));
                orlist.add(cb.like(root.get("captain"), "%" + query.getQueryName() + "%"));
                list.add(cb.or(orlist.toArray(new Predicate[0])));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, page.sortPageDefault("watchTime"));

        PageResult<WatchDto> pageResult = new PageResult<>();
        pageResult.setTotal(results.getTotalElements());
        pageResult.setRows(WatchParser.toDtoList(results.getContent()));
        return pageResult;
    }

    @Override
    @Transactional
    public WatchDto detail(LocalDate watchTime) {
        List<JzWatch> watches = watchRepo.findByWatchTime(watchTime, watchTime);
        if (CollectionUtils.isEmpty(watches)) {
            return null;
        }
        return WatchParser.toDto(watches.get(0));
    }

    @Override
    public void drop(Long id) {
        watchRepo.delete(new JzWatch(id));
    }
}
