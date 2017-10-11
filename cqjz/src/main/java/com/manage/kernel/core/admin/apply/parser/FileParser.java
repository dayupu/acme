package com.manage.kernel.core.admin.apply.parser;

import com.manage.base.utils.StringUtil;
import com.manage.kernel.core.admin.apply.dto.FileDto;
import com.manage.kernel.jpa.entity.ResourceFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/8/26.
 */
public class FileParser {


    public static FileDto toDto(ResourceFile file) {
        return toDto(file, new FileDto());
    }

    public static List<FileDto> toDtoList(List<ResourceFile> files) {
        List<FileDto> fileDtos = new ArrayList<>();
        for (ResourceFile file : files) {
            fileDtos.add(toDto(file, new FileDto()));
        }
        return fileDtos;
    }

    private static FileDto toDto(ResourceFile file, FileDto fileDto) {
        fileDto.setId(file.getId());
        fileDto.setOriginName(file.getOriginName());
        fileDto.setAccessUrl(file.getAccessUrl());
        fileDto.setHumanSize(StringUtil.fileSize(file.getFileSize()));
        return fileDto;
    }
}
