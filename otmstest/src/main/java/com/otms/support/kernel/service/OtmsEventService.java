package com.otms.support.kernel.service;

import com.otms.support.controller.define.JobSheetRequest;
import com.otms.support.kernel.dto.JobSheetEventDto;
import com.otms.support.kernel.dto.OrderEventDto;
import com.otms.support.kernel.entity.JobSheetEvent;
import com.otms.support.kernel.entity.OrderEvent;
import com.otms.support.kernel.parser.JobSheetEventParser;
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

    public void addOrderEvent(OrderEventDto orderEventDto) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderNumber(orderEventDto.getOrderNumber());
        orderEvent.setErpNumber(orderEventDto.getErpNumber());
        orderEvent.setEventId(orderEventDto.getEventId());
        orderEvent.setDamage(orderEventDto.getDamage());
        orderEvent.setLoss(orderEventDto.getLoss());
        orderEvent.setEventTime(orderEventDto.getEventTime());
        orderEvent.setEventType(orderEventDto.getEventType());
        orderEvent.setLatitude(orderEventDto.getLatitude());
        orderEvent.setLongitude(orderEventDto.getLongitude());
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

    public PageResult<JobSheetEventDto> jobSheetEventList(PageQuery pageQuery, JobSheetEventDto query) {
        Page<JobSheetEvent> JobsheetPage = jobSheetEventRepo.findAll((Specification<JobSheetEvent>) (root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getMark())) {
                list.add(cb.like(root.get("mark").as(String.class), "%" + query.getMark() + "%"));
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
        pageResult.setRows(JobSheetEventParser.toUserDtoList(JobsheetPage.getContent()));
        return pageResult;
    }
}
