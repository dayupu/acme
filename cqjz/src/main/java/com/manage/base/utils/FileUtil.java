package com.manage.base.utils;

import com.manage.base.supplier.Pair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;

/**
 * Created by bert on 17-8-25.
 */
public class FileUtil {

    private static String IMAGE_BASE64_PREFIX = "data:image/%s;base64,%s";

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

    public static synchronized String genFileId() {
        return "FILE_" + System.currentTimeMillis();
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

    /**
     * 获取文件名后缀(例:png)
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

        return fileName.substring(index + 1);
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


    public static String imageByteToBase64(byte[] bytes, String suffix) {
        if (bytes == null || bytes.length == 0 || suffix == null) {
            return null;
        }
        return String.format(IMAGE_BASE64_PREFIX, suffix, Base64Utils.encodeToString(bytes));
    }

    public static Pair<byte[], String> imageBase64ToByte(String base64) {
        Matcher matcher = Pattern.compile("data:([^/]*)/([^;]*);base64,(.*)").matcher(base64);
        if (!matcher.find()) {
            return null;
        }
        Pair<byte[], String> imagePair = new Pair<>();
        imagePair.setLeft(Base64Utils.decodeFromString(matcher.group(3)));
        imagePair.setRight(matcher.group(2));
        return imagePair;
    }

}
