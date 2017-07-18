package com.manage.news.core.admin.service.impl;

import com.manage.base.bean.Pair;
import com.manage.base.exceptions.BusinessException;
import com.manage.cache.bean.TokenUser;
import com.manage.news.core.admin.service.UserService;
import com.manage.news.dto.UserDto;
import com.manage.news.jpa.kernel.entity.Role;
import com.manage.news.jpa.kernel.entity.User;
import com.manage.news.jpa.kernel.repository.UserRepository;
import com.manage.news.spring.message.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void add(UserDto userDto) {

        User user = new User();

        User query = userRepository.findUserByAccount(userDto.getAccount());
        if (query != null) {
            throw new BusinessException(msg("user.register.user.exists"));
        }

        user.setAccount(userDto.getAccount());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
    }

    @Override
    public void modify(UserDto userDto) {

    }

    @Override
    @Transactional
    public Pair<User, List<Long>> authUserDetail(String account) {

        User user = userRepository.findUserByAccount(account);

        List<Long> roleIds = new ArrayList<Long>();
        for (Role role : user.getRoles()) {
            roleIds.add(role.getId());
        }

        return new Pair<User, List<Long>>(user,roleIds);
    }

    private String msg(String code, Object... params) {
        return Messages.get(code, params);
    }
}