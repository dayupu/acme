package com.manage.kernel.core.admin.parser;


import com.manage.kernel.core.admin.dto.UserDto;
import com.manage.kernel.jpa.news.entity.User;

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
        userDto.setAccount(user.getAccount());
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        return userDto;
    }
}
