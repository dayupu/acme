package com.manage.kernel.spring.comm;

import com.manage.plugins.cache.CacheManager;
import com.manage.kernel.jpa.entity.AdPermission;
import com.manage.kernel.jpa.entity.AdRole;
import com.manage.kernel.jpa.repository.AdPermissionRepo;
import com.manage.kernel.jpa.repository.ResourceBundleRepo;
import com.manage.kernel.jpa.repository.AdRoleRepo;
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
    private AdRoleRepo roleRepo;

    @Autowired
    private AdPermissionRepo permissionRepo;

    @Autowired
    private ResourceBundleRepo resourceBundleRepo;

    @Override
    public void afterPropertiesSet() throws Exception {

        cacheRoleDatas();

    }

    private void cacheRoleDatas() {

        Iterable<AdRole> roles = roleRepo.findAll();
        List<String> privileges = new ArrayList<String>();
        for (AdRole role : roles) {
            for (AdPermission permission : role.getPermissions()) {
                privileges.add(permission.getCode());
            }
            cacheManager.put(PREFIX_ROLE + role.getId(), privileges);
        }
    }

}
