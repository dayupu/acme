package com.manage.news.core.admin.service.impl;

import com.manage.news.core.admin.service.PermissionService;
import com.manage.news.jpa.kernel.entity.Permission;
import com.manage.news.jpa.kernel.repository.PermissionRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    @Transactional
    public void mergePermission(List<Permission> permissions) {

        Iterable<Permission> existPermissions = permissionRepository.findAll();

        Map<String, Permission> existPermissionMap = new HashMap<String, Permission>();
        for (Permission permission : existPermissions) {
            existPermissionMap.put(permission.getCode(), permission);
        }

        for (Permission permission : permissions) {
            if (existPermissionMap.containsKey(permission.getCode())) {
                continue;
            }
            permissionRepository.save(permission);
        }
    }
}
