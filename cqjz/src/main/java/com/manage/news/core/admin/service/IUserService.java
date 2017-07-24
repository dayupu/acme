package com.manage.news.core.admin.service;

import com.manage.base.bean.Pair;
import com.manage.cache.bean.TokenUser;
import com.manage.news.dto.UserDto;
import com.manage.news.jpa.kernel.entity.User;

import java.util.List;
import java.util.Map;

public interface IUserService {

    void add(UserDto userDto);

    void modify(UserDto userDto);

    Pair<User, List<Long>> authUserDetail(String account);
}
