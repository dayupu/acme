package com.manage.kernel.core.admin.view.comm;

import com.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by bert on 2017/10/29.
 */
@Controller
@RequestMapping("/ueditor")
public class UeditorController {

    @RequestMapping(value = "/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("application/json");
        //String rootPath = request.getSession().getServletContext().getRealPath("/");
        String rootPath = ClassUtils.getDefaultClassLoader().getResource("").getPath(); ;
        try {

            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
