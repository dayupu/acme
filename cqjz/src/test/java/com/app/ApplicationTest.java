package com.app;

import com.manage.application.StartApplication;
import com.manage.news.core.admin.service.IMenuService;
import com.manage.news.spring.SpringUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {StartApplication.class})
public class ApplicationTest {


    @Test
    public void launcher(){
        IMenuService IMenuService = SpringUtils.getBean(IMenuService.class);
        List<Long> roleIds = new ArrayList<Long>(){{add(1L);}};
        IMenuService.menuListByRoleIds(roleIds);
    }


}