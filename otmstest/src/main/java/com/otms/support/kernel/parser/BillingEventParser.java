package com.otms.support.kernel.parser;

import com.otms.support.kernel.dto.BillingEventDto;
import com.otms.support.kernel.dto.OrderEventDto;
import com.otms.support.kernel.entity.BillingEvent;
import com.otms.support.kernel.entity.OrderEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-9-12.
 */
public class BillingEventParser {

    public static List<BillingEventDto> toDtoList(List<BillingEvent> events) {
        List<BillingEventDto> dtos = new ArrayList<>();
        for (BillingEvent event : events) {
            dtos.add(toDto(event, new BillingEventDto()));
        }
        return dtos;
    }

    private static BillingEventDto toDto(BillingEvent event, BillingEventDto dto) {
        dto.setCreatedOn(event.getCreatedOn());
        dto.setMessage(event.getMessage());
        return dto;
    }
}
