package com.manage.base.utils;

import com.manage.base.constant.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.UUID;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;
import org.joda.time.LocalDate;
import org.springframework.security.access.method.P;
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

    public static synchronized String genDateDir() {
        return LocalDate.now().toString("yyyyMMdd") + File.separator;
    }

    public static synchronized String genImageId() {
        return "IMAGE_" + System.currentTimeMillis();
    }

    public static String joinPath(String... paths) {
        if (paths == null && paths.length == 0) {
            return null;
        }

        String head = paths[0];
        if (head.endsWith(File.separator)) {
            head = head.substring(0, head.length() - 1);
        }

        StringBuilder builder = new StringBuilder(head);
        for (int i = 1; i < paths.length; i++) {
            if (!paths[i].startsWith(File.separator)) {
                builder.append(File.separator);
            }
            builder.append(paths[i]);
        }
        return builder.toString();
    }

    public static String generateFileId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "0");
    }

    public static void main(String[] args) {
        String a = "/resource/image/{0}";
        System.out.println(MessageFormat.format(a, "1"));
    }

    /**
     * 获取文件名后缀(例:.png)
     *
     * @param fileName
     * @return
     */
    public static String suffix(String fileName) {

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
