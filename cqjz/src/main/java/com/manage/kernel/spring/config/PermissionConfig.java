package com.manage.kernel.spring.config;

import com.manage.base.database.enums.Permit;
import com.manage.base.database.enums.PermitType;
import com.manage.kernel.core.admin.service.comm.IPermissionService;
import com.manage.kernel.jpa.entity.AdPermission;
import com.manage.kernel.spring.annotation.UserPermit;
import com.manage.kernel.spring.annotation.UserPermitGroup;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
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
    private IPermissionService permissionService;

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("[Permission] auto merge: {}", autoMerge);
        if (!autoMerge) {
            return;
        }

        LOGGER.info("[Permission] scan packages: {}", basePackages);
        List<String> classNames = getClassNames();
        Class clazz;
        Permit permit;
        Permit parentPermit;
        UserPermit userPermit;
        UserPermitGroup userPermitGroup;
        AdPermission permission;
        List<AdPermission> permissionList = new ArrayList<AdPermission>();
        for (String className : classNames) {
            clazz = Class.forName(className);
            userPermitGroup = (UserPermitGroup) clazz.getAnnotation(UserPermitGroup.class);
            if (userPermitGroup == null) {
                continue;
            }

            parentPermit = userPermitGroup.value();
            for (Method method : clazz.getMethods()) {
                userPermit = method.getAnnotation(UserPermit.class);
                if (userPermit == null) {
                    continue;
                }

                permit = userPermit.value();
                if (userPermit.group() != Permit.GROUP_DEFAULT) {
                    parentPermit = userPermit.group();
                }

                permission = new AdPermission();
                permission.setCode(parentPermit.getCode() + permit.getCode());
                permission.setPermit(permit);
                permission.setMessageKey(permit.messageKey());
                permission.setParentCode(parentPermit.getCode());
                permission.setType(permit.getType());
                permission.setCreatedOn(new Date());
                permissionList.add(permission);
            }
        }

        LOGGER.info("[Permission] ready merge permission data.");
        mergePermission(permissionList);
        LOGGER.info("[Permission] merge permission data success.");
    }

    private void mergePermission(List<AdPermission> permissionList) {
        List<AdPermission> permissions = new ArrayList<AdPermission>();
        Map<String, Boolean> existMap = new HashMap<String, Boolean>();

        AdPermission permissionGroup;
        for (Permit permit : Permit.values()) {
            if (permit.getType() != PermitType.GROUP) {
                continue;
            }
            permissionGroup = new AdPermission();
            permissionGroup.setCode(permit.getCode());
            permissionGroup.setPermit(permit);
            permissionGroup.setMessageKey(permit.messageKey());
            permissionGroup.setType(permit.getType());
            permissionGroup.setCreatedOn(new Date());
            permissions.add(permissionGroup);
        }

        for (AdPermission permission : permissionList) {
            if (existMap.containsKey(permission.getCode())) {
                continue;
            }
            permissions.add(permission);
            existMap.put(permission.getCode(), true);
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
