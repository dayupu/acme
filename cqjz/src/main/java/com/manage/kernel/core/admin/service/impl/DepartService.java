package com.manage.kernel.core.admin.service.impl;

import com.manage.base.supplier.TreeNode;
import com.manage.base.utils.CoreUtils;
import com.manage.base.utils.StringUtils;
import com.manage.kernel.core.admin.dto.DepartDto;
import com.manage.kernel.core.admin.service.IDepartService;
import com.manage.kernel.jpa.news.entity.Department;
import com.manage.kernel.jpa.news.repository.DepartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bert on 2017/8/20.
 */
@Service
public class DepartService implements IDepartService {

    @Autowired
    private DepartRepo departRepo;

    @Override
    public List<TreeNode> departTree() {
        return null;
    }

    @Override
    public DepartDto getDepart(Long id) {
        return null;
    }

    @Override
    public DepartDto updateDepart(Long id, DepartDto departDto) {
        return null;
    }

    @Override
    public void deleteDepart(Long id) {

    }

    @Override
    public void addDepart(DepartDto departDto) {
        Department department = new Department();
        department.setName(departDto.getName());
        department.setCode(CoreUtils.departCodeSimple(departDto.getCode()));
        department.setFullCode(CoreUtils.departCodeFull(departDto.getCode()));
        department.setLevel(CoreUtils.departLevel(departDto.getCode()));
        department.setLeaf(true);
        if (StringUtils.isBlank(departDto.getParentCode())) {
            int count = departRepo.queryListByLevelCount(department.getLevel());
            department.setSequence(count + 1);
        } else {
            List<Department> departments = departRepo.queryListByParentCode(CoreUtils.departCodeSimple(departDto.getParentCode()));
            department.setSequence(departments.size() + 1);

            Department parent = departRepo.findOne(departDto.getParentCode());
            if (parent.isLeaf()) {
                parent.setLeaf(false);
                departRepo.save(parent);
            }
        }
        departRepo.save(department);
    }
}
