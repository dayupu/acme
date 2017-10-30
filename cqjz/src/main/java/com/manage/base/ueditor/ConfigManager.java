package com.manage.base.ueditor;

import com.manage.base.utils.JsonUtil;
import com.manage.kernel.spring.UeditorSupplier;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Configuration;

/**
 * Created by bert on 17-10-30.
 */
public class ConfigManager {

    private static ConfigManager configManager;
    private UeditorSupplier ueditorSupplier;
    private String rootPath;

    private ConfigManager(UeditorSupplier ueditorSupplier, String rootPath) {
        this.ueditorSupplier = ueditorSupplier;
        this.rootPath = rootPath;
    }

    public static ConfigManager instance(UeditorSupplier ueditorSupplier, String rootPath) {
        if (configManager == null) {
            configManager = new ConfigManager(ueditorSupplier, rootPath);
        }
        return configManager;
    }

    public String allConfig() {
        return JsonUtil.toJson(this.ueditorSupplier);
    }

    public Map<String, Object> getConfig(int type) {
        HashMap conf = new HashMap();
        String savePath = null;
        switch (type) {
        case 1:
            conf.put("isBase64", "false");
            conf.put("maxSize", Long.valueOf(ueditorSupplier.getImageMaxSize()));
            conf.put("allowFiles", ueditorSupplier.getImageAllowFiles());
            conf.put("fieldName", ueditorSupplier.getImageFieldName());
            savePath = ueditorSupplier.getImagePathFormat();
            break;
        case 2:
            conf.put("filename", "scrawl");
            conf.put("maxSize", Long.valueOf(ueditorSupplier.getScrawlMaxSize()));
            conf.put("fieldName", ueditorSupplier.getScrawlFieldName());
            conf.put("isBase64", "true");
            savePath = ueditorSupplier.getScrawlPathFormat();
            break;
        case 3:
            conf.put("maxSize", Long.valueOf(ueditorSupplier.getVideoMaxSize()));
            conf.put("allowFiles", ueditorSupplier.getVideoAllowFiles());
            conf.put("fieldName", ueditorSupplier.getVideoFieldName());
            savePath = ueditorSupplier.getVideoPathFormat();
            break;
        case 4:
            conf.put("isBase64", "false");
            conf.put("maxSize", Long.valueOf(ueditorSupplier.getFileMaxSize()));
            conf.put("allowFiles", ueditorSupplier.getFileAllowFiles());
            conf.put("fieldName", ueditorSupplier.getFileFieldName());
            savePath = ueditorSupplier.getFilePathFormat();
            break;
        case 5:
            conf.put("filename", "remote");
            conf.put("filter", ueditorSupplier.getCatcherLocalDomain());
            conf.put("maxSize", Long.valueOf(ueditorSupplier.getCatcherMaxSize()));
            conf.put("allowFiles", ueditorSupplier.getCatcherAllowFiles());
            conf.put("fieldName", ueditorSupplier.getCatcherFieldName() + "[]");
            savePath = ueditorSupplier.getCatcherPathFormat();
            break;
        case 6:
            conf.put("allowFiles", ueditorSupplier.getFileManagerAllowFiles());
            conf.put("dir", ueditorSupplier.getFileManagerListPath());
            conf.put("count", Integer.valueOf(ueditorSupplier.getFileManagerListSize()));
            break;
        case 7:
            conf.put("allowFiles", ueditorSupplier.getImageManagerAllowFiles());
            conf.put("dir", ueditorSupplier.getImageManagerListPath());
            conf.put("count", Integer.valueOf(ueditorSupplier.getImageManagerListSize()));
        }

        conf.put("savePath", savePath);
        conf.put("rootPath", this.rootPath);
        return conf;
    }
}
