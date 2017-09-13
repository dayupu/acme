package com.otms.support.kernel.service;

import com.otms.support.controller.define.JobSheetRequest;
import com.otms.support.controller.define.OrderEventRequest;
import com.otms.support.kernel.dto.BillingEventDto;
import com.otms.support.kernel.dto.JobSheetEventDto;
import com.otms.support.kernel.dto.OrderEventDto;
import com.otms.support.kernel.entity.BillingEvent;
import com.otms.support.kernel.entity.JobSheetEvent;
import com.otms.support.kernel.entity.OrderEvent;
import com.otms.support.kernel.parser.BillingEventParser;
import com.otms.support.kernel.parser.JobSheetEventParser;
import com.otms.support.kernel.parser.OrderEventParser;
import com.otms.support.kernel.repository.BillingEventRepo;
import com.otms.support.kernel.repository.JobSheetEventRepo;
import com.otms.support.kernel.repository.OrderEventRepo;
import com.otms.support.spring.model.PageQuery;
import com.otms.support.supplier.model.PageResult;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-9-11.
 */
@Service
public class OtmsEventService {

    @Autowired
    private JobSheetEventRepo jobSheetEventRepo;

    @Autowired
    private OrderEventRepo orderEventRepo;

    @Autowired
    private BillingEventRepo billingEventRepo;

    @Transactional
    public void addOrderEvent(OrderEventRequest request, String mark) {
        if (CollectionUtils.isEmpty(request.getEvents())) {
            return;
        }

        OrderEvent orderEvent = new OrderEvent();
        for (OrderEventRequest.Event event : request.getEvents()) {
            orderEvent = new OrderEvent();
            orderEvent.setMark(mark);
            orderEvent.setEventId(event.getEventId());
            orderEvent.setOrderNumber(event.getOrderNumber());
            orderEvent.setErpNumber(event.getErpNumber());
            orderEvent.setLoss(event.getLoss());
            orderEvent.setDamage(event.getDamage());
            orderEvent.setEventTime(event.getEventTime());
            orderEvent.setEventType(event.getEventType());
            orderEvent.setLatitude(event.getLatitude());
            orderEvent.setLongitude(event.getLongitude());
            orderEvent.setTruckPlate(event.getTruckPlate());
            orderEvent.setRemark(event.getRemark());
            if (!CollectionUtils.isEmpty(event.getFileNames())) {
                StringBuilder builder = new StringBuilder();
                builder.append("[");
                for (String fileName : event.getFileNames()) {
                    builder.append(fileName).append(",");
                }
                builder.setLength(builder.length() - 1);
                builder.append("]");
                orderEvent.setFileNames(builder.toString());
            }
            orderEvent.setCreatedOn(LocalDateTime.now());
            orderEventRepo.save(orderEvent);
        }

    }

    @Transactional
    public void addJobSheetEvent(JobSheetRequest request, String mark) {

        if (CollectionUtils.isEmpty(request.getEvents())) {
            return;
        }
        JobSheetEvent jobSheet;
        for (JobSheetRequest.Event event : request.getEvents()) {
            jobSheet = new JobSheetEvent();
            jobSheet.setMark(mark);
            jobSheet.setEventId(event.getEventId());
            jobSheet.setEventTime(event.getEventTime());
            jobSheet.setEventType(event.getEventType());
            jobSheet.setJobSheetNumber(event.getJobSheetNumber());
            jobSheet.setExternalShipmentId(event.getExternalShipmentId());
            jobSheet.setCreatedOn(LocalDateTime.now());
            jobSheetEventRepo.save(jobSheet);
        }
    }

    @Transactional
    public void addBillingEvent(String request, String mark) {
        BillingEvent event = new BillingEvent();
        event.setMark(mark);
        event.setCreatedOn(LocalDateTime.now());
        event.setMessage(request);
        billingEventRepo.save(event);
    }

    public PageResult<JobSheetEventDto> jobSheetEventList(PageQuery pageQuery, JobSheetEventDto query) {
        Page<JobSheetEvent> JobsheetPage = jobSheetEventRepo
                .findAll((Specification<JobSheetEvent>) (root, criteriaQuery, cb) -> {
                    List<Predicate> list = new ArrayList<>();
                    if (!StringUtils.isEmpty(query.getMark())) {
                        list.add(cb.like(root.get("mark").as(String.class), query.getMark() + "%"));
                    }
                    if (!StringUtils.isEmpty(query.getJobSheetNumber())) {
                        list.add(cb.like(root.get("jobSheetNumber").as(String.class), query.getJobSheetNumber() + "%"));
                    }
                    if (query.getEventTimeBegin() != null) {
                        list.add(cb.greaterThanOrEqualTo(root.get("eventTime").as(LocalDateTime.class),
                                query.getEventTimeBegin()));
                    }
                    if (query.getEventTimeEnd() != null) {
                        list.add(cb.lessThanOrEqualTo(root.get("eventTime").as(LocalDateTime.class),
                                query.getEventTimeEnd()));
                    }
                    return cb.and(list.toArray(new Predicate[0]));
                }, pageQuery.sortPageDefault("id"));

        PageResult<JobSheetEventDto> pageResult = new PageResult<JobSheetEventDto>();
        pageResult.setTotal(JobsheetPage.getTotalElements());
        pageResult.setRows(JobSheetEventParser.toDtoList(JobsheetPage.getContent()));
        return pageResult;
    }

    public PageResult<OrderEventDto> orderEventList(PageQuery pageQuery, OrderEventDto query) {
        Page<OrderEvent> eventPage = orderEventRepo.findAll((Specification<OrderEvent>) (root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getMark())) {
                list.add(cb.like(root.get("mark").as(String.class), query.getMark() + "%"));
            }
            if (!StringUtils.isEmpty(query.getOrderNumber())) {
                list.add(cb.like(root.get("orderNumber").as(String.class), query.getOrderNumber() + "%"));
            }
            if (!StringUtils.isEmpty(query.getErpNumber())) {
                list.add(cb.like(root.get("erpNumber").as(String.class), query.getErpNumber() + "%"));
            }
            if (query.getEventTimeBegin() != null) {
                list.add(cb.greaterThanOrEqualTo(root.get("eventTime").as(LocalDateTime.class),
                        query.getEventTimeBegin()));
            }
            if (query.getEventTimeEnd() != null) {
                list.add(cb.lessThanOrEqualTo(root.get("eventTime").as(LocalDateTime.class), query.getEventTimeEnd()));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, pageQuery.sortPageDefault("id"));

        PageResult<OrderEventDto> pageResult = new PageResult<OrderEventDto>();
        pageResult.setTotal(eventPage.getTotalElements());
        pageResult.setRows(OrderEventParser.toDtoList(eventPage.getContent()));
        return pageResult;
    }

    public PageResult<BillingEventDto> billingEventList(PageQuery pageQuery, BillingEventDto query) {
        Page<BillingEvent> eventPage = billingEventRepo
                .findAll((Specification<BillingEvent>) (root, criteriaQuery, cb) -> {
                    List<Predicate> list = new ArrayList<>();
                    if (!StringUtils.isEmpty(query.getMark())) {
                        list.add(cb.like(root.get("mark").as(String.class), query.getMark() + "%"));
                    }

                    if (query.getCreatedOnBegin() != null) {
                        list.add(cb.greaterThanOrEqualTo(root.get("createdOn").as(LocalDateTime.class),
                                query.getCreatedOnBegin()));
                    }
                    if (query.getCreatedOnEnd() != null) {
                        list.add(cb.lessThanOrEqualTo(root.get("createdOn").as(LocalDateTime.class),
                                query.getCreatedOnEnd()));
                    }
                    return cb.and(list.toArray(new Predicate[0]));
                }, pageQuery.sortPageDefault("id"));

        PageResult<BillingEventDto> pageResult = new PageResult<BillingEventDto>();
        pageResult.setTotal(eventPage.getTotalElements());
        pageResult.setRows(BillingEventParser.toDtoList(eventPage.getContent()));
        return pageResult;
    }
}
