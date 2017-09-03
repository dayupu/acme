package com.manage.kernel.core.admin.service.system;

import com.manage.base.supplier.PageResult;
import com.manage.base.supplier.Pair;
import com.manage.base.supplier.TreeNode;
import com.manage.kernel.core.admin.dto.RoleDto;
import com.manage.kernel.core.admin.dto.UserDto;
import com.manage.kernel.jpa.news.entity.User;
import com.manage.kernel.spring.entry.PageQuery;

import java.util.List;

public interface IRoleService {

    void addRole(RoleDto roleDto);

    void modifyRole(RoleDto roleDto);

    RoleDto getRole(Long roleId);

    void deleteRole(Long roleId);

    Pair<List<TreeNode>, List<TreeNode>> rolePrivilege(Long roleId);

    PageResult<RoleDto> getRoleListByPage(PageQuery pageQuery, RoleDto roleQuery);

    void resetPrivilege(RoleDto roleDto);
}
