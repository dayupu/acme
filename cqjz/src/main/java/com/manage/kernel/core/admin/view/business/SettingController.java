package com.manage.kernel.core.admin.view.business;

import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.utils.Validators;
import com.manage.kernel.core.admin.apply.dto.UserDto;
import com.manage.kernel.core.admin.service.system.IUserService;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.spring.comm.SessionHelper;
import org.activiti.engine.identity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bert on 17-10-16.
 */
@RestController
@RequestMapping("/admin/setting")
public class SettingController {


    private static final Logger LOGGER = LogManager.getLogger(SettingController.class);

    @Autowired
    private IUserService userService;

    @GetMapping("/self")
    public ResponseInfo getSessionUser() {
        ResponseInfo response = new ResponseInfo();
        AdUser user = SessionHelper.user();
        UserDto userDto = userService.getUser(user.getAccount());
        response.wrapSuccess(userDto);
        return response;
    }

    @PutMapping("/self")
    public ResponseInfo editSessionUser(@RequestBody UserDto userDto) {
        ResponseInfo response = new ResponseInfo();
        try {

            Validators.notBlank(userDto.getAccount());
            AdUser user = SessionHelper.user();
            Validators.isTrue(user.getAccount().equals(userDto.getAccount()));
            userDto = userService.editSessionUser(userDto);
            response.wrapSuccess(userDto, MessageInfos.SAVE_SUCCESS);
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

    @PutMapping("/self/pwd")
    public ResponseInfo changeSessionUserPwd(@RequestBody UserDto userDto) {
        ResponseInfo response = new ResponseInfo();
        try {

            Validators.notBlank(userDto.getAccount());
            Validators.notBlank(userDto.getPassword());
            Validators.notBlank(userDto.getPasswordNew());
            AdUser user = SessionHelper.user();
            Validators.isTrue(user.getAccount().equals(userDto.getAccount()));
            userService.changePassword(userDto);
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
