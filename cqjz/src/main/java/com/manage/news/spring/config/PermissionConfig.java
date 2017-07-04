package com.manage.news.spring.config;

import com.manage.base.enums.Privilege;
import com.manage.base.enums.PrivilegeGroup;
import com.manage.news.core.admin.service.PermissionService;
import com.manage.news.jpa.kernel.entity.Permission;
import com.manage.news.spring.annotation.TokenPermission;
import com.manage.news.spring.annotation.TokenPermissionGroup;
import com.manage.news.spring.base.SpringConstants;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

public class PermissionConfig implements InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger(PermissionConfig.class);

    private static final String RESOURCE_PATTERN = "/**/*.class";

    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private String[] basePackages;

    private boolean autoMerge = false;

    @Autowired
    private PermissionService permissionService;

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("[Permission] auto merge: {}", autoMerge);
        if (!autoMerge) {
            return;
        }

        LOGGER.info("[Permission] scan packages: {}", basePackages);
        List<String> classNames = getClassNames();
        Class clazz;
        Privilege privilege;
        PrivilegeGroup privilegeGroup;
        TokenPermission tokenPermission;
        TokenPermissionGroup permissionGroup;
        Map<String, Permission> permissionMap = new HashMap<String, Permission>();
        for (String className : classNames) {
            clazz = Class.forName(className);
            permissionGroup = (TokenPermissionGroup) clazz.getAnnotation(TokenPermissionGroup.class);
            if (permissionGroup == null) {
                continue;
            }

            privilegeGroup = permissionGroup.value();
            for (Method method : clazz.getMethods()) {
                tokenPermission = method.getAnnotation(TokenPermission.class);
                if (tokenPermission == null) {
                    continue;
                }

                privilege = tokenPermission.value();
                if (tokenPermission.group() != PrivilegeGroup.DEFAULT) {
                    privilegeGroup = tokenPermission.group();
                }

                if (!permissionMap.containsKey(privilege.getCode())) {
                    permissionMap.put(privilege.getCode(),
                            new Permission(privilege.getCode(), privilegeGroup.getCode(), privilege.getResourceKey(),
                                    SpringConstants.PERMISSION_TYPE_FUNCTION));
                }
            }
        }

        LOGGER.info("[Permission] ready merge permission data.");
        mergePermission(permissionMap);
        LOGGER.info("[Permission] merge permission data success.");
    }

    private void mergePermission(Map<String, Permission> permissionMap) {
        List<Permission> permissions = new ArrayList<Permission>();
        for (PrivilegeGroup privilegeGroup : PrivilegeGroup.values()) {
            permissions.add(new Permission(privilegeGroup.getCode(), null, privilegeGroup.getResourceKey(),
                    SpringConstants.PERMISSION_TYPE_GROUP));
        }

        for (Permission permission : permissionMap.values()) {
            permissions.add(permission);
        }

        permissionService.mergePermission(permissions);

    }

    private List<String> getClassNames() {
        List<String> classNames = new ArrayList<String>();
        try {
            if (basePackages == null) {
                throw new Exception("[Permission] packagesToScan is required property.");
            }
            for (String packages : basePackages) {
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils
                        .convertClassNameToResourcePath(packages) + RESOURCE_PATTERN;
                Resource[] resources = resourcePatternResolver.getResources(pattern);
                MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
                for (Resource resource : resources) {
                    MetadataReader reader = null;
                    reader = readerFactory.getMetadataReader(resource);
                    classNames.add(reader.getClassMetadata().getClassName());
                }
            }
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return classNames;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

    public void setAutoMerge(boolean autoMerge) {
        this.autoMerge = autoMerge;
    }
}
