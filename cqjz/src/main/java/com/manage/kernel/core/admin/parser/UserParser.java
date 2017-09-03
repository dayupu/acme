package com.manage.kernel.core.admin.parser;

import com.manage.kernel.core.admin.dto.UserDto;
import com.manage.kernel.jpa.news.entity.User;
import com.manage.kernel.spring.comm.Messages;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class UserParser {

    public static UserDto toUserDto(User user) {
        return toUserDto(user, new UserDto());
    }

    public static List<UserDto> toUserDtoList(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(toUserDto(user, new UserDto()));
        }
        return userDtos;
    }

    private static UserDto toUserDto(User user, UserDto userDto) {
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
        return userDto;
    }
}
