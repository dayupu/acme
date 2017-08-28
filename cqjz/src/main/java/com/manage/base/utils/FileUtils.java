package com.manage.base.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.manage.kernel.core.admin.model.FileModel;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import sun.plugin2.util.SystemUtil;

/**
 * Created by bert on 17-8-25.
 */
public class FileUtils {

    public static void save(InputStream inputStream, String filePath) throws Exception {
        save(inputStream, new File(filePath));
    }

    public static void save(InputStream inputStream, File file) throws Exception {
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

    public static synchronized String generateName(String extensionName) {
        long time = System.currentTimeMillis();
        if (extensionName != null && !extensionName.startsWith(".")) {
            extensionName = "." + extensionName;
        }
        return time + extensionName;
    }

    public static String generateFileId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "0");
    }

    public static String extensionName(String fileName) {

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
