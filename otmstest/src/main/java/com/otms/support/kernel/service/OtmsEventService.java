package com.otms.support.kernel.service;

import com.otms.support.kernel.dto.OrderEventDto;
import com.otms.support.kernel.entity.OrderEvent;
import com.otms.support.kernel.repository.OrderEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bert on 17-9-11.
 */
@Service
public class OtmsEventService {


    @Autowired
    private OrderEventRepo orderEventRepo;

    private void saveOrderEvent(OrderEventDto orderEventDto){

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
}
