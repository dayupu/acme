package com.manage.kernel.core.comm.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by bert on 2017/9/26.
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @RequestMapping("/image/{id}")
    public void showImage(HttpServletResponse response, @PathVariable("id") Long id) {
        response.setContentType("img/jpeg");
        response.setCharacterEncoding("utf-8");
        try {
            File file = new File("G:/timg.jpg");
            OutputStream outputStream = response.getOutputStream();
            InputStream in = new FileInputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
