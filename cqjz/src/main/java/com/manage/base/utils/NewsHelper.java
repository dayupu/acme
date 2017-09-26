package com.manage.base.utils;

import java.util.regex.Pattern;

/**
 * Created by bert on 2017/9/26.
 */
public class NewsHelper {



    public void fetchImageUrl(String content) {

    }

    public static void main(String[] args) {

        Pattern pattern = Pattern.compile("upload/\\d{10,15}\\.");
        String content = "<p><img src=\"http://localhost:9999/cqjz/upload/1506434660557.jpg\" width=\"495\" height=\"206\"/></p>";


    }
}
