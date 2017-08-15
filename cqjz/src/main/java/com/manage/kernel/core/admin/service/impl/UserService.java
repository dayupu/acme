package com.manage.kernel.core.admin.service.impl;

import com.manage.base.exception.UserNotFoundException;
import com.manage.base.supplier.PageResult;
import com.manage.base.supplier.Pair;
import com.manage.base.exception.CoreException;
import com.manage.base.supplier.msgs.MessageErrors;
import com.manage.base.utils.EnumUtils;
import com.manage.base.utils.StringUtils;
import com.manage.kernel.core.admin.dto.UserDto;
import com.manage.kernel.core.admin.parser.UserParser;
import com.manage.kernel.core.admin.service.IUserService;
import com.manage.kernel.jpa.news.entity.Role;
import com.manage.kernel.jpa.news.entity.User;
import com.manage.kernel.jpa.news.repository.UserRepo;
import com.manage.kernel.spring.comm.Messages;
import com.manage.kernel.spring.comm.ServiceBase;
import com.manage.kernel.spring.config.security.AuthPasswordEncoder;
import com.manage.kernel.spring.entry.PageQuery;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends ServiceBase implements IUserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto getUser(Long userId) {

        User user = userRepo.findOne(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }

        return UserParser.toUserDto(user);
    }

    @Override
    @Transactional
    public PageResult<UserDto> getUserListByPage(PageQuery pageQuery, UserDto userQuery) {

        Page<User> userPage = userRepo.findAll((Specification<User>) (root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.isNotBlank(userQuery.getAccount())) {
                list.add(cb.equal(root.get("account").as(String.class), userQuery.getAccount()));
            }
            if (StringUtils.isNotBlank(userQuery.getName())) {
                list.add(cb.like(root.get("name").as(String.class), "%" + userQuery.getName() + "%"));
            }
            if (StringUtils.isNotBlank(userQuery.getMobile())) {
                list.add(cb.equal(root.get("mobile").as(String.class), userQuery.getMobile()));
            }
            if (StringUtils.isNotNull(userQuery.getCreatedAt())) {
                list.add(cb.greaterThanOrEqualTo(root.get("createdAt").as(LocalDateTime.class), userQuery.getCreatedAt()));
            }
            if (StringUtils.isNotNull(userQuery.getCreatedAtEnd())) {
                list.add(cb.lessThanOrEqualTo(root.get("createdAt").as(LocalDateTime.class), userQuery.getCreatedAtEnd()));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, pageQuery.buildPageRequest(true));


        PageResult<UserDto> pageResult = new PageResult<UserDto>();
        pageResult.setTotal(userPage.getTotalElements());
        pageResult.setRows(UserParser.toUserDtoList(userPage.getContent()));
        return pageResult;
    }

    @Override
    @Transactional
    public void addUser(UserDto userDto) {
        User query = userRepo.findUserByAccount(userDto.getAccount());
        if (query != null) {
            throw new CoreException(MessageErrors.USER_HAS_EXISTS);
        }
        User user = new User();
        user.setAccount(userDto.getAccount());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setGender(EnumUtils.toGender(userDto.getGenderValue()));
        user.setMobile(userDto.getMobile());
        user.setTelephone(userDto.getTelephone());
        user.setEmail(userDto.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedUser(currentUser());
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void modifyUser(UserDto userDto) {
        User user = userRepo.findOne(userDto.getId());
        if (user == null) {
            throw new UserNotFoundException();
        }

        user.setName(userDto.getName());
        user.setGender(EnumUtils.toGender(userDto.getGenderValue()));
        user.setMobile(userDto.getMobile());
        user.setTelephone(userDto.getTelephone());
        user.setEmail(userDto.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedUser(currentUser());
        userRepo.save(user);
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

}
