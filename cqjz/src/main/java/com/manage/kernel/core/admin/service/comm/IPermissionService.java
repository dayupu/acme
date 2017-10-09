package com.manage.kernel.core.admin.service.comm;

import com.manage.kernel.jpa.entity.AdPermission;
import java.util.List;

public interface IPermissionService {

    void mergePermission(List<AdPermission> permissions);
}
