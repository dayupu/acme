package com.otms.support.kernel.parser;

import com.otms.support.kernel.dto.JobSheetEventDto;
import com.otms.support.kernel.entity.JobSheetEvent;

import java.util.ArrayList;
import java.util.List;

public class JobSheetEventParser {

    public static List<JobSheetEventDto> toDtoList(List<JobSheetEvent> events) {
        List<JobSheetEventDto> dtos = new ArrayList<>();
        for (JobSheetEvent event : events) {
            dtos.add(toDto(event, new JobSheetEventDto()));
        }
        return dtos;
    }

    private static JobSheetEventDto toDto(JobSheetEvent event, JobSheetEventDto dto) {
        dto.setMark(event.getMark());
        dto.setEventId(event.getEventId());
        dto.setJobSheetNumber(event.getJobSheetNumber());
        dto.setExternalShipmentId(event.getExternalShipmentId());
        dto.setEventTime(event.getEventTime());
        dto.setEventType(event.getEventType());
        return dto;
    }
}
