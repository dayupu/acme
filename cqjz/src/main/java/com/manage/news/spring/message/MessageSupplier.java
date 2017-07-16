package com.manage.news.spring.message;

import com.manage.news.jpa.kernel.entity.ResourceBundle;
import com.manage.news.jpa.kernel.repository.ResourceBundleRepository;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

public class MessageSupplier extends AbstractMessageSource implements ResourceLoaderAware, InitializingBean {

    private ResourceLoader resourceLoader;

    private static final String MAP_SPLIT_CODE = "|";

    private Map<String, String> properties = new HashMap<String, String>();

    @Autowired
    private ResourceBundleRepository resourceBundleRepository;

    public void reload() {
        properties.clear();
        properties.putAll(loadTexts());
    }

    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, Locale.CHINESE);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        reload();
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader == null ? new DefaultResourceLoader() : resourceLoader;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getText(code, locale);
        return createMessageFormat(msg, locale);
    }

    private Map<String, String> loadTexts() {
        Map<String, String> mapResource = new HashMap<String, String>();
        List<Resource> resources = this.getResource();
        for (Resource item : resources) {
            String code = item.getName() + MAP_SPLIT_CODE + item.getLanguage();
            mapResource.put(code, item.getText());
        }
        return mapResource;
    }

    private List<Resource> getResource() {

        Iterable<ResourceBundle> dbResources = resourceBundleRepository.findAll();
        List<Resource> resources = new ArrayList<Resource>();
        Resource resource;
        for (ResourceBundle dbResource : dbResources) {
            resource = new Resource();
            resource.setResourceId(dbResource.getId());
            resource.setName(dbResource.getKey());
            resource.setText(dbResource.getValue());
            resource.setLanguage(dbResource.getLocale().getLanguage());
            resources.add(resource);
        }
        return resources;
    }

    private String getText(String code, Locale locale) {
        String localeCode = locale.getLanguage();
        String key = code + MAP_SPLIT_CODE + localeCode;
        String localeText = properties.get(key);
        String resourceText = code;

        if (localeText != null) {
            resourceText = localeText;
        } else {
            localeCode = Locale.ENGLISH.getLanguage();
            key = code + MAP_SPLIT_CODE + localeCode;
            localeText = properties.get(key);
            if (localeText != null) {
                resourceText = localeText;
            } else {
                try {
                    if (getParentMessageSource() != null) {
                        resourceText = getParentMessageSource().getMessage(code, null, locale);
                    }
                } catch (Exception e) {
                    logger.error("Cannot find message with code: " + code);
                }
            }
        }
        return resourceText;
    }

}
