package com.manage.news.core.admin.service;

import com.manage.news.jpa.kernel.entity.Permission;
import java.util.List;

public interface IPermissionService {

    void mergePermission(List<Permission> permissions);

    void test();
}
