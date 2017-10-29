package com.manage.kernel.core.admin.view.comm;

import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.enums.ResponseStatus;
import com.manage.kernel.basic.model.Newest;
import com.manage.kernel.core.model.dto.*;
import com.manage.kernel.core.admin.service.business.IFlowService;
import com.manage.kernel.core.admin.service.business.INewsService;
import com.manage.kernel.core.admin.service.system.IMenuService;
import com.manage.kernel.core.admin.service.system.IUserService;
import com.manage.kernel.spring.comm.Messages;
import com.manage.kernel.spring.comm.SessionHelper;
import com.manage.kernel.spring.config.security.AuthUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/index")
public class IndexController {

    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);

    @Autowired
    private IMenuService IMenuService;

    @Autowired
    private IUserService userService;

    @Autowired
    private INewsService newsService;

    @Autowired
    private IFlowService flowService;

    @GetMapping("/menuList")
    public ResponseInfo getMenuList() {
        ResponseInfo responseInfo = new ResponseInfo();
        AuthUser authUser = SessionHelper.authUser();
        List<MenuNav> menuNavs = IMenuService.menuListByRoleIds(authUser.getRoleIds());
        responseInfo.status = ResponseStatus.SUCCESS;
        responseInfo.content = menuNavs;
        return responseInfo;
    }

    @GetMapping("/user")
    public ResponseInfo getUser() {
        ResponseInfo response = new ResponseInfo();
        try {
            UserDto user = userService.getUser(SessionHelper.user().getAccount());
            response.wrapSuccess(user);
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @GetMapping("/newest")
    public ResponseInfo home() {
        ResponseInfo response = new ResponseInfo();
        HomeDto home = new HomeDto();

        List<NewsDto> newsDtos = newsService.newestNews(SessionHelper.user());
        List<Newest<NewsDto>> newses = new ArrayList<>();
        for (NewsDto dto : newsDtos) {
            String info = Messages
                    .get("text.home.newest.news", dto.getCreatedByOrgan(), dto.getCreatedBy(), dto.getTitle());
            newses.add(new Newest(dto.getApprovedTime(), info, dto));
        }

        NewestFlowDto newestFlow = flowService.newestFlow(SessionHelper.user());
        List<Newest<FlowDto>> flows = new ArrayList<>();
        for (FlowDto dto : newestFlow.getPendingTask()) {
            String info = Messages
                    .get("text.home.newest.flow.apply", dto.getApplyUserOrgan(), dto.getApplyUser(), dto.getSubject());
            flows.add(new Newest(dto.getReceiveTime(), info, dto));
        }
        for (FlowDto dto : newestFlow.getRejectTask()) {
            String info = Messages.get("text.home.newest.flow.reject", dto.getSubject());
            flows.add(new Newest(dto.getRejectTime(), info, dto));
        }
        home.setFlows(flows);
        home.setNewses(newses);
        response.wrapSuccess(home);
        return response;
    }

    @GetMapping("/message")
    public ResponseInfo homeMessage() {
        ResponseInfo response = new ResponseInfo();
        FlowNotification notification = flowService.notification(SessionHelper.user());
        response.wrapSuccess(notification);
        return response;
    }
}
