package com.manage.kernel.core.admin.service;

import com.manage.kernel.jpa.news.entity.Permission;
import java.util.List;

public interface IPermissionService {

    void mergePermission(List<Permission> permissions);

    void test();
}
