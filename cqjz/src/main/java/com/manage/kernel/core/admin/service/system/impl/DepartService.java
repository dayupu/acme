package com.manage.kernel.core.admin.service.system.impl;

import com.manage.base.exception.CoreException;
import com.manage.base.exception.DepartNotFoundException;
import com.manage.base.supplier.page.TreeNode;
import com.manage.base.supplier.msgs.MessageErrors;
import com.manage.base.utils.CoreUtil;
import com.manage.base.utils.StringUtil;
import com.manage.kernel.core.admin.apply.dto.DepartDto;
import com.manage.kernel.core.admin.apply.parser.DepartParser;
import com.manage.kernel.core.admin.service.system.IDepartService;
import com.manage.kernel.jpa.news.entity.Department;
import com.manage.kernel.jpa.news.repository.DepartRepo;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bert on 2017/8/20.
 */
@Service
public class DepartService implements IDepartService {

    private static final Logger LOGGER = LogManager.getLogger(DepartService.class);

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
            LOGGER.info("Not found the department {}", code);
            throw new DepartNotFoundException();
        }
        return DepartParser.toDepartDto(department);
    }

    @Override
    @Transactional
    public DepartDto updateDepart(DepartDto departDto) {

        Department department = departRepo.findOne(departDto.getCode());
        if (department == null) {
            LOGGER.info("Not found the department {}", departDto.getCode());
            throw new DepartNotFoundException();
        }

        department.setName(departDto.getName());
        departRepo.save(department);
        return DepartParser.toDepartDto(department);
    }

    @Override
    @Transactional
    public void deleteDepart(String code) {
        Department department = departRepo.findOne(code);
        if (department == null) {
            LOGGER.info("Not found the department {}", code);
            throw new DepartNotFoundException();
        }

        if (!department.getChildrens().isEmpty()) {
            LOGGER.info("The department {} has childrens, delete failed.", code);
            throw new CoreException(MessageErrors.DEPART_HAS_CHILDREN);
        }

        departRepo.delete(department);
    }

    @Override
    @Transactional
    public void addDepart(DepartDto departDto) {
        Department department = new Department();
        department.setName(departDto.getName());
        department.setCode(CoreUtil.departCodeSimple(departDto.getCode()));
        department.setFullCode(CoreUtil.departCodeFull(departDto.getCode()));
        department.setLevel(CoreUtil.departLevel(departDto.getCode()));
        department.setLeaf(true);
        if (StringUtil.isNotBlank(departDto.getParentCode())) {
            Department parent = departRepo.findOne(departDto.getParentCode());
            if (parent == null) {
                LOGGER.info("Not found the department {}", departDto.getParentCode());
                throw new DepartNotFoundException();
            }
            department.setParent(parent);
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

        List<Department> departments = departRepo.queryListByParentCode(CoreUtil.departCodeSimple(code));
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
