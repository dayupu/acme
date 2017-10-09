package com.manage.kernel.core.admin.service.comm.impl;

import com.manage.plugins.cache.CacheManager;
import com.manage.kernel.core.admin.service.comm.IPermissionService;
import com.manage.kernel.jpa.entity.AdPermission;
import com.manage.kernel.jpa.repository.AdPermissionRepo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    AdPermissionRepo permissionRepo;

    @Autowired
    private CacheManager cacheManager;

    @Override
    @Transactional
    public void mergePermission(List<AdPermission> permissions) {

        Iterable<AdPermission> existPermissions = permissionRepo.findAll();

        Map<String, Boolean> existMap = new HashMap<String, Boolean>();
        for (AdPermission permission : existPermissions) {
            existMap.put(permission.getCode(), true);
        }

        for (AdPermission permission : permissions) {
            if (existMap.containsKey(permission.getCode())) {
                continue;
            }
            permissionRepo.save(permission);
        }
    }
}
