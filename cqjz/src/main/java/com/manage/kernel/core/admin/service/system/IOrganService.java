package com.manage.kernel.core.admin.service.system;

import com.manage.base.supplier.page.TreeNode;
import com.manage.kernel.core.model.dto.OrganDto;

import java.util.List;

public interface IOrganService {

    List<TreeNode> organTree();

    OrganDto getOrgan(String code);

    OrganDto updateOrgan(OrganDto organDto);

    void deleteOrgan(String code);

    void addOrgan(OrganDto organDto);
}
