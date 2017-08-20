package com.manage.kernel.core.admin.view.system;

import com.manage.base.exception.CoreException;
import com.manage.base.exception.DepartNotFoundException;
import com.manage.base.exception.ValidateException;
import com.manage.base.supplier.ResponseInfo;
import com.manage.base.supplier.TreeNode;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.utils.Validators;
import com.manage.kernel.core.admin.dto.DepartDto;
import com.manage.kernel.core.admin.dto.MenuDto;
import com.manage.kernel.core.admin.service.IDepartService;
import com.manage.kernel.spring.annotation.InboundLog;
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

import java.util.List;

@RestController
@RequestMapping("/admin/depart")
public class DepartController {

    private static final Logger LOGGER = LogManager.getLogger(DepartController.class);

    @Autowired
    private IDepartService departService;

    @InboundLog
    @GetMapping("{id}")
    public ResponseInfo departDetail(@PathVariable("id") Long id) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(id);
            DepartDto departDto = departService.getDepart(id);
            if (departDto == null) {
                throw new DepartNotFoundException();
            }
            response.wrapSuccess(departDto);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @PutMapping("{id}")
    public ResponseInfo departEdit(@PathVariable("id") Long id, @RequestBody DepartDto departDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(id);
            Validators.notNull(departDto);
            DepartDto result = departService.updateDepart(id, departDto);
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
    public ResponseInfo departDrop(@PathVariable("id") Long id) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(id);
            departService.deleteDepart(id);
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
    @GetMapping("/treeList")
    public ResponseInfo getDepartTree() {
        ResponseInfo response = new ResponseInfo();
        try {
            List<TreeNode> treeNodes = departService.departTree();
            response.wrapSuccess(treeNodes);
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }


    @InboundLog
    @PostMapping
    public ResponseInfo addDepart(@RequestBody DepartDto departDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(departDto);
            Validators.notEmpty(departDto.getName());
            departService.addDepart(departDto);
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
