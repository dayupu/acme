package com.manage.kernel.core.admin.service.impl;

import com.manage.base.supplier.PageResult;
import com.manage.base.supplier.Pair;
import com.manage.base.exception.CoreException;
import com.manage.kernel.core.admin.dto.UserDto;
import com.manage.kernel.core.admin.parser.UserParser;
import com.manage.kernel.core.admin.service.IUserService;
import com.manage.kernel.jpa.news.entity.Role;
import com.manage.kernel.jpa.news.entity.User;
import com.manage.kernel.jpa.news.repository.UserRepo;
import com.manage.kernel.spring.comm.Messages;
import com.manage.kernel.spring.entry.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public PageResult<UserDto> getUserListByPage(PageQuery pageQuery) {

        Page<User> userPage = userRepo.findAll((Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        }, pageQuery.buildPageRequest(true));


        PageResult<UserDto> pageResult = new PageResult<UserDto>();
        pageResult.setTotal(userPage.getTotalElements());
        pageResult.setRows(UserParser.toUserDtoList(userPage.getContent()));
        return pageResult;
    }

    @Override
    public void add(UserDto userDto) {

        User user = new User();

        User query = userRepo.findUserByAccount(userDto.getAccount());
        if (query != null) {
            throw new CoreException(msg("user.register.user.exists"));
        }

        user.setAccount(userDto.getAccount());
        user.setPassword(userDto.getPassword());
        userRepo.save(user);
    }

    @Override
    public void modify(UserDto userDto) {

    }

    @Override
    @Transactional
    public Pair<User, List<Long>> authUserDetail(String account) {

        User user = userRepo.findUserByAccount(account);
        List<Long> roleIds = new ArrayList<Long>();
        for (Role role : user.getRoles()) {
            roleIds.add(role.getId());
        }

        return new Pair<User, List<Long>>(user, roleIds);
    }

    private String msg(String code, Object... params) {
        return Messages.get(code, params);
    }
}
