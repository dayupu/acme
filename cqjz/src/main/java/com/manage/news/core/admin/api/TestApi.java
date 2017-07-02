package com.manage.news.core.admin.api;

import com.manage.news.spring.annotation.TokenAuthentication;
import com.manage.news.token.TokenService;
import com.manage.news.token.base.TokenUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/test", consumes = "application/json", produces = "application/json")
@Api(value = "测试类",tags = "测试接口")
public class TestApi {

    @Autowired
    private TokenService tokenService;

    @TokenAuthentication
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("测试")
    public Map test() {
        return new HashMap();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("用户登录")
    public Map login(HttpServletRequest request) {
        request.getRemoteHost();
        TokenUser tokenUser = new TokenUser();
        tokenUser.setAccount("aab");
        String tokenId = tokenService.register(tokenUser, request.getRemoteHost());
        System.out.println(tokenId);
        return new HashMap();
    }
}
