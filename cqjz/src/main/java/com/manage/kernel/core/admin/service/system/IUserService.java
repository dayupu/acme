package com.manage.kernel.core.admin.service.system;

import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.Pair;
import com.manage.base.supplier.page.TreeNode;
import com.manage.kernel.core.model.dto.UserDto;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.base.supplier.page.PageQuery;

import java.util.List;

public interface IUserService {

    UserDto modifyUser(UserDto userDto);

    UserDto getUser(String account);

    UserDto getUser(Long userId);

    Pair<AdUser, List<Long>> authUserDetail(String account);

    PageResult<UserDto> getUserListByPage(PageQuery pageQuery, UserDto userQuery);

    Pair<UserDto, List<TreeNode>> userRolePair(Long userId);

    void resetUserRole(UserDto userDto);

    void modifyUserStatus(UserDto userDto);

    UserDto editSessionUser(UserDto userDto);

    void changePassword(UserDto userDto);
}
