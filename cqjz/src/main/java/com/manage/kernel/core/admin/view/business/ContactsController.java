package com.manage.kernel.core.admin.view.business;

import com.manage.base.enums.ResponseStatus;
import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.utils.Validators;
import com.manage.kernel.core.admin.service.business.IContactsService;
import com.manage.kernel.core.admin.service.business.IWatchService;
import com.manage.kernel.core.model.dto.ContactsDto;
import com.manage.kernel.core.model.dto.FlowDto;
import com.manage.kernel.core.model.dto.NewsDto;
import com.manage.kernel.core.model.dto.WatchDto;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.annotation.PageQueryAon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bert on 17-10-13.
 */
@RestController
@RequestMapping("/admin/jz/contacts")
public class ContactsController {

    private static final Logger LOGGER = LogManager.getLogger(ContactsController.class);

    @Autowired
    private IContactsService contactsService;

    @InboundLog
    @GetMapping
    public ResponseInfo contactsInfo() {
        ResponseInfo response = new ResponseInfo();
        response.wrapSuccess(contactsService.contactsInfo());
        return response;
    }

    @InboundLog
    @PostMapping
    public ResponseInfo saveContacts(@RequestBody ContactsDto contactsDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notBlank(contactsDto.getContent());
            response.wrapSuccess(contactsService.saveContacts(contactsDto), MessageInfos.SAVE_SUCCESS);
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
