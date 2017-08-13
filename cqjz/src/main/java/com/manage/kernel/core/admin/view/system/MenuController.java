package com.manage.kernel.core.admin.view.system;

import com.manage.base.exception.MenuNotFoundException;
import com.manage.base.supplier.ResponseInfo;
import com.manage.base.supplier.TreeNode;
import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.utils.Validators;
import com.manage.kernel.core.admin.dto.MenuDto;
import com.manage.kernel.core.admin.dto.MenuNav;
import com.manage.kernel.core.admin.service.IMenuService;
import com.manage.kernel.spring.annotation.InboundLog;
import static java.awt.SystemColor.menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            Validators.notNull(id, null);
            MenuDto menuDto = menuService.getMenu(id);
            if (menuDto == null) {
                throw new MenuNotFoundException();
            }
            response.wrapSuccess(menuDto);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @PutMapping("{id}")
    public ResponseInfo modifyMenu(@PathVariable("id") Long id, @RequestBody MenuDto menuObj) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(id, null);
            Validators.notNull(menuObj, null);
            MenuDto menu = menuService.updateMenu(id, menuObj);
            if (menu == null) {
                throw new CoreException();
            }
            response.wrapSuccess(menu, MessageInfos.SAVE_SUCCESS);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (CoreException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @DeleteMapping("{id}")
    public ResponseInfo menuDelete(@PathVariable("id") Long id) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(id, null);
            menuService.deleteMenu(id);
            response.wrapSuccess(null, MessageInfos.DELETE_SUCCESS);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (CoreException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @GetMapping("/treeList")
    public ResponseInfo getMenuTree() {
        ResponseInfo response = new ResponseInfo();
        try {
            List<TreeNode> treeNodes = menuService.menuTree();
            response.wrapSuccess(treeNodes);
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @GetMapping("/location")
    public ResponseInfo getLocation(@RequestParam("url") String url) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(url, null);
            List<MenuNav> locations = menuService.menuLocation(url);
            response.wrapSuccess(locations);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @PostMapping("/addSub")
    public ResponseInfo addSubMenu(@RequestBody MenuDto menuDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(menuDto, null);
            Validators.notNull(menuDto.getParentId(), null);
            Validators.notEmpty(menuDto.getName(), null);
            menuService.addSubMenu(menuDto);
            response.wrapSuccess(null, MessageInfos.SAVE_SUCCESS);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (CoreException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }
}
