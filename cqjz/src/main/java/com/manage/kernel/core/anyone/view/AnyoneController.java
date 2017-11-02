package com.manage.kernel.core.anyone.view;

import com.manage.base.database.enums.NewsType;
import com.manage.base.supplier.bootstrap.PageQueryBS;
import com.manage.base.supplier.bootstrap.PageResultBS;
import com.manage.kernel.core.admin.service.business.IContactsService;
import com.manage.kernel.core.anyone.service.IAnyoneService;
import com.manage.kernel.core.model.dto.ContactsDto;
import com.manage.kernel.core.model.vo.HomeVo;
import com.manage.kernel.core.model.vo.NewsDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bert on 17-7-28.
 */
@RestController
@RequestMapping("/free")
public class AnyoneController {

    @Autowired
    private IAnyoneService anyoneService;

    @Autowired
    private IContactsService contactsService;

    @GetMapping("/home")
    public HomeVo home() {
        return anyoneService.homeDetail();
    }

    @GetMapping("/newsInfo/{number}")
    public NewsDetailVo newsInfo(@PathVariable("number") String number) {
        return anyoneService.newsDetail(number);
    }

    @PostMapping("/newsList/{type}")
    public PageResultBS newsList(@PathVariable("type") String type, @RequestBody PageQueryBS pageQuery) {
        return anyoneService.newsList(NewsType.fromTypeName(type), pageQuery);
    }
    @PostMapping("/superstarList")
    public PageResultBS newsList( @RequestBody PageQueryBS pageQuery) {
        return anyoneService.superstarList(pageQuery);
    }

    @GetMapping("/contacts")
    public ContactsDto contacts() {
        return contactsService.contactsInfo();
    }
}
