package com.manage.news.core.admin.service;

import com.manage.cache.bean.TokenUser;
import com.manage.news.dto.UserDto;

public interface UserService {

    TokenUser login(String user, String password);

    void add(UserDto userDto);

    void modify(UserDto userDto);
}
