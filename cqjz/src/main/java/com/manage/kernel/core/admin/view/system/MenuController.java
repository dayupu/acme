package com.manage.kernel.core.admin.view.system;

import com.manage.base.atomic.PageResult;
import com.manage.base.atomic.ResponseInfo;
import com.manage.base.extend.enums.ResponseStatus;
import com.manage.kernel.core.admin.dto.MenuDto;
import com.manage.kernel.core.admin.parser.MenuDtoParser;
import com.manage.kernel.core.admin.service.IMenuService;
import com.manage.kernel.jpa.news.entity.Menu;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.annotation.PageQueryAon;
import com.manage.kernel.spring.entry.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/system")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @InboundLog
    @GetMapping("/menuList")
    public ResponseInfo getMenuList(@PageQueryAon PageQuery pageQuery) {

        ResponseInfo responseInfo = new ResponseInfo();
        Page<Menu> menuPage = menuService.menuList(pageQuery);

        PageResult<MenuDto> pageResult = new PageResult<MenuDto>(menuPage.getTotalElements(),
                MenuDtoParser.toMenuDtoList(menuPage.getContent()));

        responseInfo.setStatus(ResponseStatus.SUCCESS);
        responseInfo.setContent(pageResult);
        return responseInfo;
    }

}
