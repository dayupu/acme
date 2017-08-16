package com.manage.kernel.core.admin.service.impl;

import com.google.common.collect.Lists;
import com.manage.base.exception.RoleNotFoundException;
import com.manage.base.extend.enums.Status;
import com.manage.base.supplier.PageResult;
import com.manage.base.supplier.TreeNode;
import com.manage.base.utils.StringUtils;
import com.manage.kernel.core.admin.dto.RoleDto;
import com.manage.kernel.core.admin.dto.UserDto;
import com.manage.kernel.core.admin.parser.RoleParser;
import com.manage.kernel.core.admin.parser.UserParser;
import com.manage.kernel.core.admin.service.IRoleService;
import com.manage.kernel.jpa.news.entity.Menu;
import com.manage.kernel.jpa.news.entity.Role;
import com.manage.kernel.jpa.news.repository.MenuRepo;
import com.manage.kernel.jpa.news.repository.RoleRepo;
import com.manage.kernel.spring.comm.ServiceBase;
import com.manage.kernel.spring.entry.PageQuery;
import java.util.ArrayList;
import java.util.List;
import static javafx.scene.input.KeyCode.L;
import javax.persistence.criteria.Predicate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bert on 17-8-15.
 */
@Service
public class RoleService extends ServiceBase implements IRoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private MenuRepo menuRepo;

    @Override
    @Transactional
    public void addRole(RoleDto roleDto) {

        Role role = new Role();
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        role.setStatus(Status.INIT);
        role.setCreatedAt(LocalDateTime.now());
        role.setCreatedUser(currentUser());

        roleRepo.save(role);
    }

    @Override
    @Transactional
    public void modifyRole(RoleDto roleDto) {

        Role role = roleRepo.findOne(roleDto.getId());
        if (role == null) {
            throw new RoleNotFoundException();
        }

        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        role.setUpdatedAt(LocalDateTime.now());
        role.setUpdatedUser(currentUser());
        roleRepo.save(role);
    }

    @Override
    @Transactional
    public RoleDto getRole(Long roleId) {

        Role role = roleRepo.findOne(roleId);
        if (role == null) {
            throw new RoleNotFoundException();
        }

        return RoleParser.toRoleDto(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        Role role = roleRepo.findOne(roleId);
        if (role == null) {
            throw new RoleNotFoundException();
        }
        roleRepo.delete(role);
    }

    @Override
    @Transactional
    public PageResult<RoleDto> getRoleListByPage(PageQuery pageQuery, RoleDto roleQuery) {
        Page<Role> rolePage = roleRepo.findAll((Specification<Role>) (root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.isNotBlank(roleQuery.getName())) {
                list.add(cb.like(root.get("name").as(String.class), "%" + roleQuery.getName() + "%"));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, pageQuery.buildPageRequest(true));

        PageResult<RoleDto> pageResult = new PageResult<RoleDto>();
        pageResult.setTotal(rolePage.getTotalElements());
        pageResult.setRows(RoleParser.toRoleDtoList(rolePage.getContent()));
        return pageResult;
    }

    @Override
    @Transactional
    public List<TreeNode> roleMenus(Long roleId) {
        Role role = roleRepo.findOne(roleId);
        if (role == null) {
            throw new RoleNotFoundException();
        }

        List<Menu> menus = menuRepo.queryMenuAll();
        List<TreeNode> roleMenus = new ArrayList<>();
        TreeNode treeNode;
        for (Menu menu : menus) {
            treeNode = new TreeNode();
            treeNode.setId(menu.getId());
            treeNode.setName(menu.getName());
            treeNode.setPid(menu.getParentId());
            for (Menu roleMenu : role.getMenus()) {
                if (roleMenu.getId().equals(menu.getId())) {
                    treeNode.setChecked(true);
                }
            }
            roleMenus.add(treeNode);
        }
        return roleMenus;
    }
}
