package com.manage.kernel.core.admin.view.base;

import com.manage.base.exception.CoreException;
import com.manage.base.exception.OrganNotFoundException;
import com.manage.base.exception.ValidateException;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.supplier.page.TreeNode;
import com.manage.base.utils.Validators;
import com.manage.kernel.core.model.dto.OrganDto;
import com.manage.kernel.core.admin.service.system.IOrganService;
import com.manage.kernel.spring.annotation.InboundLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/organ")
public class OrganController {

    private static final Logger LOGGER = LogManager.getLogger(OrganController.class);

    @Autowired
    private IOrganService organService;

    @InboundLog
    @GetMapping("{code}")
    public ResponseInfo getOrgan(@PathVariable("code") String code) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(code);
            OrganDto organDto = organService.getOrgan(code);
            if (organDto == null) {
                throw new OrganNotFoundException();
            }
            response.wrapSuccess(organDto);
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
    @PutMapping("{code}")
    public ResponseInfo editOrgan(@PathVariable("code") String code, @RequestBody OrganDto organDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(code);
            Validators.notNull(organDto);
            organDto.setCode(code);
            OrganDto result = organService.updateOrgan(organDto);
            if (result == null) {
                throw new CoreException();
            }
            response.wrapSuccess(result, MessageInfos.SAVE_SUCCESS);
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
    @DeleteMapping("{code}")
    public ResponseInfo dropOrgan(@PathVariable("code") String code) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(code);
            organService.deleteOrgan(code);
            response.wrapSuccess(null, MessageInfos.DELETE_SUCCESS);
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
    @PostMapping
    public ResponseInfo addOrgan(@RequestBody OrganDto organDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(organDto);
            Validators.notEmpty(organDto.getName());
            organService.addOrgan(organDto);
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
    @GetMapping("/treeList")
    public List<TreeNode> treeRoot() {
        return organService.organTree();
    }

}
