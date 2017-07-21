package com.manage.news.core.admin.service.impl;

import com.manage.cache.CacheManager;
import com.manage.news.core.admin.service.PermissionService;
import com.manage.news.jpa.kernel.entity.Permission;
import com.manage.news.jpa.kernel.repository.PermissionRepo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionServiceImpl implements PermissionService {

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
