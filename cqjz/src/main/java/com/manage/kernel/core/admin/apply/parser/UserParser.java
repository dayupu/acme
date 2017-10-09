package com.manage.kernel.core.admin.apply.parser;

import com.manage.kernel.core.admin.apply.dto.UserDto;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.spring.comm.Messages;

import java.util.ArrayList;
import java.util.List;

public class UserParser {

    public static UserDto toUserDto(AdUser user) {
        return toUserDto(user, new UserDto());
    }

    public static List<UserDto> toUserDtoList(List<AdUser> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (AdUser user : users) {
            userDtos.add(toUserDto(user, new UserDto()));
        }
        return userDtos;
    }

    private static UserDto toUserDto(AdUser user, UserDto userDto) {
        userDto.setId(user.getId());
        userDto.setAccount(user.getAccount());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setTelephone(user.getTelephone());
        userDto.setMobile(user.getMobile());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        userDto.setGender(user.getGender());
        userDto.setStatus(Messages.get(user.getStatus()));
        userDto.setCreatedBy(user.getCreatedUserName());
        userDto.setUpdatedBy(user.getUpdatedUserName());
        userDto.setApproveRole(user.getApproveRole());
        return userDto;
    }
}
