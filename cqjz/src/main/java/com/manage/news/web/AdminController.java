package com.manage.news.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @RequestMapping
    public String admin(Model model, HttpSession session) {
        return "admin/login";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, String username, String password) {
        if (username == "a") {
            return "admin/login";
        }
        return "admin/index";
    }

}
