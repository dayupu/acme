package com.manage.kernel.core.admin.service.system;

import com.manage.base.supplier.page.TreeNode;
import com.manage.kernel.core.admin.apply.dto.OrganDto;

import java.util.List;

public interface IOrganService {

    List<TreeNode> organTree();

    OrganDto getOrgan(Long id);

    OrganDto updateOrgan(OrganDto organDto);

    void deleteOrgan(Long id);

    void addOrgan(OrganDto organDto);
}
