package com.manage.kernel.core.anyone.view;

import com.manage.base.database.enums.NewsType;
import com.manage.kernel.core.anyone.service.IAnyoneService;
import com.manage.kernel.core.model.vo.HomeVo;
import com.manage.kernel.core.model.vo.NewsDetailVo;
import com.manage.kernel.core.model.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-7-28.
 */
@RestController
@RequestMapping("/free")
public class AnyoneController {

    @Autowired
    private IAnyoneService anyoneService;

    @GetMapping("/home")
    public HomeVo home() {
        return anyoneService.homeDetail();
    }

    @GetMapping("/newsInfo/{number}")
    public NewsDetailVo newsInfo(@PathVariable("number") String number) {
        return anyoneService.newsDetail(number);
    }

    @GetMapping("/newsList/{type}")
    public List<NewsVo> newsList(@PathVariable("type") String type) {
        return anyoneService.newsList(NewsType.fromTypeName(type));
    }
}
