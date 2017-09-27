package com.manage.kernel.core.admin.service.system;

import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.Pair;
import com.manage.base.supplier.page.TreeNode;
import com.manage.kernel.core.admin.apply.dto.RoleDto;
import com.manage.base.supplier.page.PageQuery;

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
