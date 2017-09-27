package com.manage.kernel.spring.comm;

import com.manage.plugins.cache.CacheManager;
import com.manage.kernel.jpa.news.entity.Permission;
import com.manage.kernel.jpa.news.entity.Role;
import com.manage.kernel.jpa.news.repository.PermissionRepo;
import com.manage.kernel.jpa.news.repository.ResourceBundleRepo;
import com.manage.kernel.jpa.news.repository.RoleRepo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppDataCache implements InitializingBean {

    public static final String PREFIX_ROLE = "ROLE-";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
    private ResourceBundleRepo resourceBundleRepo;

    @Override
    public void afterPropertiesSet() throws Exception {

        cacheRoleDatas();

    }

    private void cacheRoleDatas() {

        Iterable<Role> roles = roleRepo.findAll();
        List<String> privileges = new ArrayList<String>();
        for (Role role : roles) {
            for (Permission permission : role.getPermissions()) {
                privileges.add(permission.getCode());
            }
            cacheManager.put(PREFIX_ROLE + role.getId(), privileges);
        }
    }

}
