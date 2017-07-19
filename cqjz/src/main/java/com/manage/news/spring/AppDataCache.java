package com.manage.news.spring;

import com.manage.cache.CacheManager;
import com.manage.news.jpa.kernel.entity.Permission;
import com.manage.news.jpa.kernel.entity.ResourceBundle;
import com.manage.news.jpa.kernel.entity.Role;
import com.manage.news.jpa.kernel.repository.PermissionRepository;
import com.manage.news.jpa.kernel.repository.ResourceBundleRepository;
import com.manage.news.jpa.kernel.repository.RoleRepository;
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
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ResourceBundleRepository resourceBundleRepository;

    @Override
    public void afterPropertiesSet() throws Exception {

        cacheRoleDatas();

    }

    private void cacheRoleDatas() {

        Iterable<Role> roles = roleRepository.findAll();
        List<Long> privileges = new ArrayList<Long>();
        for (Role role : roles) {
            for (Permission permission : role.getPermissions()) {
                privileges.add(permission.getId());
            }
            cacheManager.put(PREFIX_ROLE + role.getId(), privileges);
        }
    }

}
