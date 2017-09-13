package com.otms.support.kernel.parser;

import com.otms.support.kernel.dto.JobSheetEventDto;
import com.otms.support.kernel.dto.OrderEventDto;
import com.otms.support.kernel.entity.JobSheetEvent;
import com.otms.support.kernel.entity.OrderEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-9-12.
 */
public class OrderEventParser {

    public static List<OrderEventDto> toDtoList(List<OrderEvent> events) {
        List<OrderEventDto> dtos = new ArrayList<>();
        for (OrderEvent event : events) {
            dtos.add(toDto(event, new OrderEventDto()));
        }
        return dtos;
    }

    private static OrderEventDto toDto(OrderEvent event, OrderEventDto dto) {

        dto.setMark(event.getMark());
        dto.setEventId(event.getEventId());
        dto.setEventType(event.getEventType());
        dto.setEventTime(event.getEventTime());
        dto.setLoss(event.getLoss());
        dto.setDamage(event.getDamage());
        dto.setLatitude(event.getLatitude());
        dto.setLongitude(event.getLongitude());
        dto.setRemark(event.getRemark());
        dto.setOrderNumber(event.getOrderNumber());
        dto.setErpNumber(event.getErpNumber());
        dto.setTruckPlate(event.getTruckPlate());
        dto.setFileNames(event.getFileNames());
        return dto;
    }
}
