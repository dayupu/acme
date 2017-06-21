package com.acme.manage.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DemoController {

    @RequestMapping
    public String index(){
       return "index";
    }
}
