package com.app;

import com.manage.app.StartApplication;
import com.manage.kernel.core.admin.service.system.IMenuService;
import com.manage.kernel.spring.comm.SpringUtils;
import static com.manage.kernel.spring.comm.SpringUtils.getBean;
import com.manage.kernel.test.EventPushService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { StartApplication.class })
public class ApplicationTest {

    @Test
    public void launcher() {
        IMenuService IMenuService = getBean(IMenuService.class);
        List<Long> roleIds = new ArrayList<Long>() {{
            add(1L);
        }};
        IMenuService.menuListByRoleIds(roleIds);
    }

    @Test
    public void pushEvent() {
        EventPushService service = SpringUtils.getBean(EventPushService.class);
        service.push();
    }
}
