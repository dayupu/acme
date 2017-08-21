package com.manage.kernel.core.admin.service.impl;

import com.manage.base.exception.DepartNotFoundException;
import com.manage.base.supplier.TreeNode;
import com.manage.base.utils.CoreUtils;
import com.manage.base.utils.StringUtils;
import com.manage.kernel.core.admin.dto.DepartDto;
import com.manage.kernel.core.admin.parser.DepartParser;
import com.manage.kernel.core.admin.service.IDepartService;
import com.manage.kernel.jpa.news.entity.Department;
import com.manage.kernel.jpa.news.repository.DepartRepo;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public DepartDto getDepart(String code) {
        Department department = departRepo.findOne(code);
        if (department == null) {
            throw new DepartNotFoundException();
        }
        return DepartParser.toDepartDto(department);
    }

    @Override
    public DepartDto updateDepart(Long id, DepartDto departDto) {
        return null;
    }

    @Override
    public void deleteDepart(Long id) {

    }

    @Override
    @Transactional
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

            Department parent = departRepo.findOne(departDto.getParentCode());
            if (parent == null) {
                throw new DepartNotFoundException();
            }

            department.setParent(parent);
            department.setSequence(parent.getChildrens().size() + 1);
            if (parent.isLeaf()) {
                parent.setLeaf(false);
                departRepo.save(parent);
            }
        }
        departRepo.save(department);
    }

    @Override
    @Transactional
    public List<TreeNode> getTreeChildrens(String code) {

        List<Department> departments = departRepo.queryListByParentCode(CoreUtils.departCodeSimple(code));
        List<TreeNode> treeNodes = new ArrayList<>();
        TreeNode treeNode;
        for (Department department : departments) {
            treeNode = new TreeNode();
            treeNode.setId(department.getCode());
            treeNode.setName(department.getName());
            treeNode.setIsParent(!department.isLeaf());
            treeNode.setPid(department.getParentCode());
            treeNodes.add(treeNode);
        }

        return treeNodes;
    }

    @Override
    @Transactional
    public List<TreeNode> getTreeRoot() {
        List<Department> departments = departRepo.queryListByLevel(1);
        List<TreeNode> treeNodes = new ArrayList<>();
        TreeNode treeNode;
        for (Department department : departments) {
            treeNode = new TreeNode();
            treeNode.setId(department.getCode());
            treeNode.setName(department.getName());
            treeNode.setIsParent(!department.isLeaf());
            treeNode.setPid(department.getParentCode());
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }
}
