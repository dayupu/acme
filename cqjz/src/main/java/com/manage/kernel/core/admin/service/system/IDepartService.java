package com.manage.kernel.core.admin.service.system;

import com.manage.base.supplier.page.TreeNode;
import com.manage.kernel.core.model.dto.DepartDto;

import java.util.List;

public interface IDepartService {

    DepartDto getDepart(String code);

    DepartDto updateDepart(DepartDto departDto);

    void deleteDepart(String code);

    void addDepart(DepartDto departDto);

    List<TreeNode> getTreeChildrens(String code);

    List<TreeNode> getTreeRoot();
}
