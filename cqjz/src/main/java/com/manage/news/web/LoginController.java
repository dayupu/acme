package com.manage.news.web;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class LoginController {


    @RequestMapping("/login")
    public String login(Model model, HttpSession session) {


        String html = "login2";

        return html;
    }

}
