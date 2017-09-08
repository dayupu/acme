package com.manage.base.utils;

import com.manage.base.constant.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;

/**
 * Created by bert on 17-8-25.
 */
public class FileUtil {

    private static String resourcePath;

    public static String resourcePath() {
        if (resourcePath == null) {
            resourcePath = ClassUtils.getDefaultClassLoader().getResource(Constants.STATIC_RESOURCE_PATH).getPath();
        }
        return resourcePath;
    }

    public static void uploadPublic(InputStream inputStream, String filePath) throws Exception {
        if (!filePath.startsWith(Constants.SEPARATOR)) {
            filePath = Constants.SEPARATOR + filePath;
        }
        upload(inputStream, resourcePath() + filePath);
    }

    public static void upload(InputStream inputStream, String filePath) throws Exception {
        upload(inputStream, new File(filePath));
    }

    public static void upload(InputStream inputStream, File file) throws Exception {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            StreamUtils.copy(inputStream, fos);
            fos.flush();
        } finally {
            inputStream.close();
            fos.close();
        }
    }

    public static synchronized String generateName(String suffix) {
        long time = System.currentTimeMillis();
        if (suffix != null && !suffix.startsWith(".")) {
            suffix = "." + suffix;
        }
        return time + suffix;
    }

    public static String generateFileId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "0");
    }

    /**
     * 获取文件名后缀(例:.png)
     * @param fileName
     * @return
     */
    public static String fileSuffix(String fileName) {

        if (fileName == null) {
            return null;
        }

        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return null;
        }

        return fileName.substring(index);
    }

    public static boolean copyFile(File origin, File newFile) {
        try {
            FileCopyUtils.copy(origin, newFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
