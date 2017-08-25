package com.manage.base.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.util.FileCopyUtils;
import sun.plugin2.util.SystemUtil;

/**
 * Created by bert on 17-8-25.
 */
public class FileUtils {

    public static void save(InputStream is, File file) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
        byte[] buff = new byte[1024];
        while (is.read(buff) != -1) {
            dos.write(buff);
        }
        dos.flush();
        is.close();
        dos.close();
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

    public static void main(String[] args) {
        System.out.println(generateFileId());
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
