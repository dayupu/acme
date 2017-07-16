package com.manage.news.core.admin.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.manage.base.bean.ResponseInfo;
import com.manage.base.enums.ResponseEnum;
import com.manage.base.exceptions.BusinessException;
import com.manage.base.utils.StringUtils;
import com.manage.cache.TokenManager;
import com.manage.news.spring.message.Messages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class AdminController {

    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/ajax/login")
    @ResponseBody
    public ResponseInfo login(HttpServletRequest request, String account, String password) {
        ResponseInfo response = new ResponseInfo();
        try {
            if (StringUtils.isEmptyAny(account, password)) {
                throw new BusinessException();
            }

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(account, password);
            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            response.status = ResponseEnum.SUCCESS;
        } catch (Exception e) {
            LOGGER.error(e);
            response.status = ResponseEnum.ERROR;
            response.message = Messages.get("login.user.or.password.error");
        }
        return response;
    }

    @RequestMapping("/admin/index")
    public ModelAndView index() {
        return new ModelAndView("admin/index");
    }
}
