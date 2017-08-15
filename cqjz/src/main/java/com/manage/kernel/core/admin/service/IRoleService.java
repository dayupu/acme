package com.manage.kernel.core.admin.service;

import com.manage.base.supplier.PageResult;
import com.manage.base.supplier.Pair;
import com.manage.kernel.core.admin.dto.UserDto;
import com.manage.kernel.jpa.news.entity.User;
import com.manage.kernel.spring.entry.PageQuery;
import java.util.List;

public interface IRoleService {

    void addRole(UserDto userDto);

    void modifyRole(UserDto userDto);

    UserDto getRole(Long userId);

    PageResult<UserDto> getRoleListByPage(PageQuery pageQuery, UserDto userQuery);
}
