package com.manage.kernel.core.model.parser;

import com.manage.kernel.core.model.dto.WatchDto;
import com.manage.kernel.jpa.entity.JzWatch;
import java.util.ArrayList;
import java.util.List;

public class WatchParser {

    public static WatchDto toDto(JzWatch watch) {
        return toDto(watch, new WatchDto());
    }

    public static List<WatchDto> toDtoList(List<JzWatch> watchList) {
        List<WatchDto> results = new ArrayList<>();
        for (JzWatch watch : watchList) {
            results.add(toDto(watch, new WatchDto()));
        }
        return results;
    }

    private static WatchDto toDto(JzWatch watch, WatchDto watchDto) {
        watchDto.setId(watch.getId());
        watchDto.setWatchTime(watch.getWatchTime());
        watchDto.setCaptain(watch.getCaptain());
        watchDto.setLeader(watch.getLeader());
        watchDto.setWorker(watch.getWorker());
        watchDto.setPhone(watch.getPhone());
        return watchDto;
    }
}
