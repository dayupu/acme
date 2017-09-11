package com.otms.support.kernel.parser;

import com.otms.support.kernel.dto.JobSheetEventDto;
import com.otms.support.kernel.entity.JobSheetEvent;

import java.util.ArrayList;
import java.util.List;

public class JobSheetEventParser {

    public static List<JobSheetEventDto> toUserDtoList(List<JobSheetEvent> events) {
        List<JobSheetEventDto> userDtos = new ArrayList<>();
        for (JobSheetEvent event : events) {
            userDtos.add(toJobSheetDto(event, new JobSheetEventDto()));
        }
        return userDtos;
    }

    private static JobSheetEventDto toJobSheetDto(JobSheetEvent event, JobSheetEventDto dto) {
        dto.setMark(event.getMark());
        dto.setEventId(event.getEventId());
        dto.setJobSheetNumber(event.getJobSheetNumber());
        dto.setExternalShipmentId(event.getExternalShipmentId());
        dto.setEventTime(event.getEventTime());
        dto.setEventType(event.getEventType());
        return dto;
    }
}
