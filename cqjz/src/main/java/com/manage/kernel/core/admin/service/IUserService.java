package com.manage.kernel.core.admin.service;

import com.manage.base.supplier.PageResult;
import com.manage.base.supplier.Pair;
import com.manage.base.supplier.TreeNode;
import com.manage.kernel.core.admin.dto.UserDto;
import com.manage.kernel.jpa.news.entity.User;
import com.manage.kernel.spring.entry.PageQuery;

import java.util.List;

public interface IUserService {

    void addUser(UserDto userDto);

    void modifyUser(UserDto userDto);

    UserDto getUser(Long userId);

    Pair<User, List<Long>> authUserDetail(String account);

    PageResult<UserDto> getUserListByPage(PageQuery pageQuery, UserDto userQuery);

    Pair<UserDto, List<TreeNode>> userRolePair(Long userId);

    void resetUserRole(UserDto userDto);

    void modifyUserStatus(UserDto userDto);
}
