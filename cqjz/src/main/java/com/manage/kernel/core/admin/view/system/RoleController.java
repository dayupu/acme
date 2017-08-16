package com.manage.kernel.core.admin.view.system;

import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.extend.enums.Permit;
import com.manage.base.extend.enums.ResponseStatus;
import com.manage.base.supplier.PageResult;
import com.manage.base.supplier.ResponseInfo;
import com.manage.base.supplier.TreeNode;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.utils.Validators;
import com.manage.kernel.core.admin.dto.RoleDto;
import com.manage.kernel.core.admin.service.IRoleService;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.annotation.PageQueryAon;
import com.manage.kernel.spring.annotation.UserPermit;
import com.manage.kernel.spring.annotation.UserPermitGroup;
import com.manage.kernel.spring.entry.PageQuery;
import java.util.List;
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
    public ResponseInfo getRoleList(@PageQueryAon PageQuery pageQuery, @RequestBody RoleDto roleQuery) {
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
            Validators.notBlank(role.getName(), null);
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
            Validators.notNull(roleId, null);
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
    public ResponseInfo modifyRole(@PathVariable("id") Long roleId, @RequestBody RoleDto role) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(roleId, null);
            Validators.notNull(role, null);
            Validators.notNull(role.getId(), null);
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
    @UserPermit(value = Permit.DELETE)
    public ResponseInfo deleteRole(@PathVariable("id") Long roleId) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(roleId, null);
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
    @GetMapping("/{id}/menuTree")
    public ResponseInfo roleMenus(@PathVariable("id") Long roleId) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(roleId, null);
            List<TreeNode> roleMenus = roleService.roleMenus(roleId);
            response.wrapSuccess(roleMenus);
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
