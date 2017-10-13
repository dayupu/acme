package com.manage.kernel.core.admin.view.business;

import com.manage.base.enums.ResponseStatus;
import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.utils.Validators;
import com.manage.kernel.core.admin.apply.dto.WatchDto;
import com.manage.kernel.core.admin.service.business.IWatchService;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.annotation.PageQueryAon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bert on 17-10-13.
 */
@RestController
@RequestMapping("/admin/jz/superstar")
public class SuperstarController {

    private static final Logger LOGGER = LogManager.getLogger(SuperstarController.class);

    @InboundLog
    @PostMapping("/list")
    public ResponseInfo userListPage(@PageQueryAon PageQuery page, @RequestBody WatchDto query) {
        ResponseInfo response = new ResponseInfo();
        return response;
    }


}
