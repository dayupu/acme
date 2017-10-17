package com.manage.kernel.core.admin.service.activiti;

import com.manage.kernel.jpa.entity.AdUser;

/**
 * Created by bert on 17-10-17.
 */
public interface IActIdentityService {

    public void saveActUser(AdUser user);
    public void initActGroup();
}
