package com.manage.kernel.core.admin.view.system;

import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.supplier.PageResult;
import com.manage.base.supplier.Pair;
import com.manage.base.supplier.ResponseInfo;
import com.manage.base.extend.enums.ResponseStatus;
import com.manage.base.supplier.TreeNode;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.utils.Validators;
import com.manage.kernel.core.admin.dto.UserDto;
import com.manage.kernel.core.admin.service.IUserService;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.annotation.PageQueryAon;
import com.manage.kernel.spring.entry.PageQuery;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/admin/user")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @InboundLog
    @PostMapping("/list")
    public ResponseInfo getUserList(@PageQueryAon PageQuery pageQuery, @RequestBody UserDto userQuery) {
        ResponseInfo response = new ResponseInfo();
        PageResult result = userService.getUserListByPage(pageQuery, userQuery);
        response.setStatus(ResponseStatus.SUCCESS);
        response.setContent(result);
        return response;
    }

    @InboundLog
    @PostMapping
    public ResponseInfo addUser(@RequestBody UserDto user) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(user.getAccount(), null);
            Validators.notNull(user.getPassword(), null);
            Validators.notBlank(user.getName(), null);
            userService.addUser(user);
            response.wrapSuccess(user, MessageInfos.SAVE_SUCCESS);
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
    public ResponseInfo getUser(@PathVariable("id") Long userId) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(userId, null);
            UserDto user = userService.getUser(userId);
            response.wrapSuccess(user);
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
    public ResponseInfo modifyUser(@PathVariable("id") Long userId, @RequestBody UserDto user) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(userId, null);
            Validators.notNull(user, null);
            Validators.notNull(user.getId(), null);
            userService.modifyUser(user);
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
    public ResponseInfo deleteUser(@PathVariable("id") Long userId) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(userId, null);
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
    @GetMapping("/{id}/role")
    public ResponseInfo userRole(@PathVariable("id") Long userId) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(userId, null);
            Pair userPair = userService.userRolePair(userId);
            Map result = new HashMap();
            result.put("user", userPair.getLeft());
            result.put("roleTree", userPair.getRight());
            response.wrapSuccess(result);
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
