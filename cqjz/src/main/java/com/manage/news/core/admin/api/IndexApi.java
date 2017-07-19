package com.manage.news.core.admin.api;


import com.manage.base.bean.ResponseInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/index")
public class IndexApi {


    @RequestMapping("/menu")
    public ResponseInfo getMenusByUser() {

        ResponseInfo responseInfo = new ResponseInfo();


        return responseInfo;
    }
}
