package com.manage.kernel.core.admin.service.impl;

import com.manage.cache.CacheManager;
import com.manage.kernel.core.admin.service.IPermissionService;
import com.manage.kernel.jpa.news.entity.Permission;
import com.manage.kernel.jpa.news.repository.PermissionRepo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    PermissionRepo permissionRepo;

    @Autowired
    private CacheManager cacheManager;

    @Override
    @Transactional
    public void mergePermission(List<Permission> permissions) {

        Iterable<Permission> existPermissions = permissionRepo.findAll();

        Map<String, Boolean> existMap = new HashMap<String, Boolean>();
        for (Permission permission : existPermissions) {
            existMap.put(permission.permitKey(), true);
        }

        for (Permission permission : permissions) {
            if (existMap.containsKey(permission.permitKey())) {
                continue;
            }
            permissionRepo.save(permission);
        }
    }

    public void test() {
        System.out.println(11);
    }
}
