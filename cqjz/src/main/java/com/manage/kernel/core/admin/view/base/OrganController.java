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
    @GetMapping("{id}")
    public ResponseInfo getOrgan(@PathVariable("id") Long id) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(id);
            OrganDto organDto = organService.getOrgan(id);
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
    @PutMapping("{id}")
    public ResponseInfo editOrgan(@PathVariable("id") Long id, @RequestBody OrganDto organDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(id);
            Validators.notNull(organDto);
            organDto.setId(id);
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
    @DeleteMapping("{id}")
    public ResponseInfo dropOrgan(@PathVariable("id") Long id) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(id);
            organService.deleteOrgan(id);
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
