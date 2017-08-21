package com.manage.kernel.core.admin.service;

import com.manage.base.supplier.TreeNode;
import com.manage.kernel.core.admin.dto.DepartDto;
import com.manage.kernel.core.admin.dto.MenuDto;
import com.manage.kernel.core.admin.dto.MenuNav;

import java.util.List;

public interface IDepartService {

    List<TreeNode> departTree();

    DepartDto getDepart(String code);

    DepartDto updateDepart(Long id, DepartDto departDto);

    void deleteDepart(Long id);

    void addDepart(DepartDto departDto);

    List<TreeNode> getTreeChildrens(String code);

    List<TreeNode> getTreeRoot();
}
