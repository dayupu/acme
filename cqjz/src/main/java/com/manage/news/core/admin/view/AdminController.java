package com.manage.news.core.admin.view;

import com.manage.base.utils.StringUtils;
import com.manage.base.utils.WebUtils;
import com.manage.news.jpa.kernel.entity.LoginLog;
import com.manage.news.jpa.kernel.repository.LoginLogRepo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.manage.base.bean.ResponseInfo;
import com.manage.base.enums.ResponseStatus;
import com.manage.base.exceptions.BusinessException;
import com.manage.cache.TokenManager;
import com.manage.news.spring.message.Messages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SessionAuthenticationStrategy sessionStrategy;

    @Autowired
    private LoginLogRepo loginLogRepo;

    @GetMapping("/login")
    @ResponseBody
    public ResponseInfo login(String account, String password, HttpServletRequest request,
            HttpServletResponse response) {
        ResponseInfo responseInfo = new ResponseInfo();
        UsernamePasswordAuthenticationToken authRequest;
        String loginResult = "success";
        try {
            if (StringUtils.isEmptyAny(account, password)) {
                throw new BusinessException();
            }

            authRequest = new UsernamePasswordAuthenticationToken(account, password);
            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            sessionStrategy.onAuthentication(authentication, request, response);
            responseInfo.status = ResponseStatus.SUCCESS;
        } catch (Exception e) {
            LOGGER.info(e);
            loginResult = "failure";
            responseInfo.status = ResponseStatus.ERROR;
            responseInfo.message = Messages.get("login.user.or.password.error");
        } finally {
            recordLoginInfo(account, request, loginResult);
        }
        return responseInfo;
    }

    private void recordLoginInfo(String account, HttpServletRequest request, String loginResult) {
        LoginLog login = new LoginLog();
        login.setAccount(account);
        login.setClientIP(WebUtils.remoteIP(request));
        login.setMessage(loginResult);
        loginLogRepo.save(login);
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("admin/index");
    }
}
