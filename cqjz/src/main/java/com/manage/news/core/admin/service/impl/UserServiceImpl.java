package com.manage.news.core.admin.service.impl;

import com.manage.base.exceptions.BusinessException;
import com.manage.cache.bean.TokenUser;
import com.manage.news.core.admin.service.UserService;
import com.manage.news.dto.UserDto;
import com.manage.news.jpa.kernel.entity.User;
import com.manage.news.jpa.kernel.repository.UserRepository;
import com.manage.news.spring.message.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public TokenUser login(String user, String password) {
        return null;
    }

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

    private String msg(String code, Object... params) {
        return Messages.get(code, params);
    }
}
