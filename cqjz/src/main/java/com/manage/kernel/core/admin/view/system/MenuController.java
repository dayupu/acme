package com.manage.kernel.core.admin.view.system;

import com.manage.base.atomic.ResponseInfo;
import com.manage.base.atomic.TreeNode;
import com.manage.base.exception.BusinessException;
import com.manage.base.extend.enums.ResponseStatus;
import com.manage.base.utils.Validator;
import com.manage.kernel.core.admin.dto.MenuDto;
import com.manage.kernel.core.admin.dto.MenuNav;
import com.manage.kernel.core.admin.service.IMenuService;
import com.manage.kernel.spring.annotation.InboundLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/menu")
public class MenuController {

    private static final Logger LOGGER = LogManager.getLogger(MenuController.class);

    @Autowired
    private IMenuService menuService;


    @InboundLog
    @GetMapping("/list/{id}")
    public ResponseInfo menuDetail(@PathVariable("id") Long id) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validator.notNull(id);
            MenuDto menuDto = menuService.getMenu(id);
            if (menuDto == null) {
                throw new BusinessException();
            }
            response.setContent(menuDto);
            response.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            LOGGER.warn(e);
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @InboundLog
    @PutMapping("list/{id}")
    public ResponseInfo menuUpdate(@PathVariable("id") Long id, @RequestBody MenuDto menuObj) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validator.notNull(id);
            Validator.notNull(menuObj);
            MenuDto menu = menuService.updateMenu(id, menuObj);
            if (menu == null) {
                throw new BusinessException();
            }
            response.setContent(menu);
            response.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            LOGGER.warn(e);
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @InboundLog
    @GetMapping("/treeList")
    public ResponseInfo getMenuTree() {
        ResponseInfo response = new ResponseInfo();
        try {
            List<TreeNode> treeNodes = menuService.menuTree();
            response.setContent(treeNodes);
            response.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            LOGGER.warn(e);
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @InboundLog
    @GetMapping("location")
    public ResponseInfo getLocation(@RequestParam("url") String url) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validator.notNull(url);
            List<MenuNav> locations = menuService.menuLocation(url);
            response.setStatus(ResponseStatus.SUCCESS);
            response.setContent(locations);
        } catch (Exception e) {
            LOGGER.warn(e);
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
