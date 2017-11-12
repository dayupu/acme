package com.manage.kernel.core.admin.service.activiti;

import com.manage.base.database.enums.ApproveRole;
import com.manage.kernel.jpa.entity.AdUser;

import java.util.List;

/**
 * Created by bert on 17-10-17.
 */
public interface IActIdentityService {

    public void saveActUser(AdUser user);

}
