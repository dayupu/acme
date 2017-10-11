package com.manage.kernel.core.admin.service.system.impl;

import com.manage.base.exception.RoleNotFoundException;
import com.manage.base.database.enums.Status;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.Pair;
import com.manage.base.supplier.page.TreeNode;
import com.manage.base.utils.StringUtil;
import com.manage.kernel.core.admin.apply.dto.RoleDto;
import com.manage.kernel.core.admin.apply.parser.RoleParser;
import com.manage.kernel.core.admin.service.system.IRoleService;
import com.manage.kernel.jpa.entity.AdMenu;
import com.manage.kernel.jpa.entity.AdPermission;
import com.manage.kernel.jpa.entity.AdRole;
import com.manage.kernel.jpa.repository.AdMenuRepo;
import com.manage.kernel.jpa.repository.AdPermissionRepo;
import com.manage.kernel.jpa.repository.AdRoleRepo;
import com.manage.kernel.spring.comm.Messages;
import com.manage.base.supplier.page.PageQuery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

import com.manage.kernel.spring.comm.SessionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class RoleService implements IRoleService {

    private static final Logger LOGGER = LogManager.getLogger(RoleService.class);

    @Autowired
    private AdRoleRepo roleRepo;

    @Autowired
    private AdMenuRepo menuRepo;

    @Autowired
    private AdPermissionRepo permissionRepo;

    @Override
    @Transactional
    public void addRole(RoleDto roleDto) {

        AdRole role = new AdRole();
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        role.setStatus(Status.INIT);
        role.setCreatedAt(LocalDateTime.now());
        role.setCreatedUser(SessionHelper.user());

        roleRepo.save(role);
    }

    @Override
    @Transactional
    public void modifyRole(RoleDto roleDto) {

        AdRole role = roleRepo.findOne(roleDto.getId());
        if (role == null) {
            LOGGER.info("Not found the role {}", roleDto.getId());
            throw new RoleNotFoundException();
        }

        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        role.setUpdatedAt(LocalDateTime.now());
        role.setUpdatedUser(SessionHelper.user());
        roleRepo.save(role);
    }

    @Override
    @Transactional
    public RoleDto getRole(Long roleId) {

        AdRole role = roleRepo.findOne(roleId);
        if (role == null) {
            LOGGER.info("Not found the role {}", roleId);
            throw new RoleNotFoundException();
        }

        return RoleParser.toDto(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        AdRole role = roleRepo.findOne(roleId);
        if (role == null) {
            LOGGER.info("Not found the role {}", roleId);
            throw new RoleNotFoundException();
        }
        roleRepo.delete(role);
    }

    @Override
    @Transactional
    public PageResult<RoleDto> getRoleListByPage(PageQuery pageQuery, RoleDto roleQuery) {
        Page<AdRole> rolePage = roleRepo.findAll((Specification<AdRole>) (root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtil.isNotBlank(roleQuery.getName())) {
                list.add(cb.like(root.get("name").as(String.class), "%" + roleQuery.getName() + "%"));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, pageQuery.sortPageDefault("id"));

        PageResult<RoleDto> pageResult = new PageResult<RoleDto>();
        pageResult.setTotal(rolePage.getTotalElements());
        pageResult.setRows(RoleParser.toDtoList(rolePage.getContent()));
        return pageResult;
    }

    @Override
    @Transactional
    public Pair rolePrivilege(Long roleId) {

        AdRole role = roleRepo.findOne(roleId);
        if (role == null) {
            LOGGER.info("Not found the role {}", roleId);
            throw new RoleNotFoundException();
        }
        TreeNode treeNode;
        List<AdMenu> menus = menuRepo.queryListAll();
        List<TreeNode> roleMenus = new ArrayList<>();
        for (AdMenu menu : menus) {
            treeNode = new TreeNode();
            treeNode.setId(menu.getId());
            treeNode.setName(menu.getName());
            treeNode.setPid(menu.getParentId());
            for (AdMenu roleMenu : role.getMenus()) {
                if (roleMenu.getId().equals(menu.getId())) {
                    treeNode.setChecked(true);
                }
            }
            roleMenus.add(treeNode);
        }

        List<AdPermission> permissions = permissionRepo.queryListAll();
        List<TreeNode> rolePermits = new ArrayList<>();
        for (AdPermission permit : permissions) {
            treeNode = new TreeNode();
            treeNode.setId(permit.getCode());
            treeNode.setName(Messages.get(permit.getMessageKey()));
            treeNode.setPid(permit.getParentCode());
            for (AdPermission permission : role.getPermissions()) {
                if (permit.getCode().equals(permission.getCode())) {
                    treeNode.setChecked(true);
                }
            }
            rolePermits.add(treeNode);
        }

        return new Pair<>(roleMenus, rolePermits);
    }

    @Override
    @Transactional
    public void resetPrivilege(RoleDto roleDto) {
        AdRole role = roleRepo.findOne(roleDto.getId());
        if (role == null) {
            LOGGER.info("Not found the role {}", roleDto.getId());
            throw new RoleNotFoundException();
        }

        List<AdPermission> permissions = new ArrayList<>();
        if (!roleDto.getPermitCodes().isEmpty()) {
            permissions = permissionRepo.queryListByCode(roleDto.getPermitCodes());
        }
        role.setPermissions(permissions);

        List<AdMenu> menus = new ArrayList<>();
        if (!roleDto.getMenuIds().isEmpty()) {
            menus = menuRepo.queryListByIds(roleDto.getMenuIds());
        }
        role.setMenus(menus);
        roleRepo.save(role);
    }

}
