package com.manage.kernel.core.admin.view.base;

import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.database.enums.Permit;
import com.manage.base.enums.ResponseStatus;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.Pair;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.utils.Validators;
import com.manage.kernel.core.model.dto.RoleDto;
import com.manage.kernel.core.admin.service.system.IRoleService;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.annotation.PageQueryAon;
import com.manage.kernel.spring.annotation.UserPermitGroup;
import com.manage.base.supplier.page.PageQuery;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/role")
@UserPermitGroup(Permit.GROUP_ROLE)
public class RoleController {

    private static final Logger LOGGER = LogManager.getLogger(RoleController.class);

    @Autowired
    private IRoleService roleService;

    @InboundLog
    @PostMapping("/list")
    public ResponseInfo roleListPage(@PageQueryAon PageQuery pageQuery, @RequestBody RoleDto roleQuery) {
        ResponseInfo response = new ResponseInfo();
        PageResult result = roleService.getRoleListByPage(pageQuery, roleQuery);
        response.setStatus(ResponseStatus.SUCCESS);
        response.setContent(result);
        return response;
    }

    @InboundLog
    @PostMapping
    public ResponseInfo addRole(@RequestBody RoleDto role) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notBlank(role.getName());
            roleService.addRole(role);
            response.wrapSuccess(role, MessageInfos.SAVE_SUCCESS);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (CoreException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @GetMapping("/{id}")
    public ResponseInfo getRole(@PathVariable("id") Long roleId) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(roleId);
            RoleDto role = roleService.getRole(roleId);
            response.wrapSuccess(role);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (CoreException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @PutMapping("/{id}")
    public ResponseInfo editRole(@PathVariable("id") Long roleId, @RequestBody RoleDto role) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(roleId);
            Validators.notNull(role);
            Validators.notNull(role.getId());
            roleService.modifyRole(role);
            response.wrapSuccess(null, MessageInfos.SAVE_SUCCESS);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (CoreException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @DeleteMapping("/{id}")
    public ResponseInfo dropRole(@PathVariable("id") Long roleId) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(roleId);
            roleService.deleteRole(roleId);
            response.wrapSuccess(null, MessageInfos.SAVE_SUCCESS);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (CoreException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @GetMapping("/{id}/privilege")
    public ResponseInfo rolePermits(@PathVariable("id") Long roleId) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(roleId);
            Pair rolePrivilege = roleService.rolePrivilege(roleId);
            Map privilege = new HashMap();
            privilege.put("roleMenus", rolePrivilege.getLeft());
            privilege.put("rolePermits", rolePrivilege.getRight());
            response.wrapSuccess(privilege);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (CoreException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @PutMapping("/{id}/privilege")
    public ResponseInfo rolePrivilege(@PathVariable("id") Long roleId, @RequestBody RoleDto roleDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(roleId);
            Validators.notNull(roleDto.getId());

            roleService.resetPrivilege(roleDto);
            response.wrapSuccess(null, MessageInfos.SAVE_SUCCESS);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (CoreException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }
}
