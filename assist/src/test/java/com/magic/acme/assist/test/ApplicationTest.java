package com.magic.acme.assist.test;

import com.magic.acme.assist.application.AssistApplication;
import com.magic.acme.assist.module.otms.service.DataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Import({AssistApplication.class})
public class ApplicationTest {

    @Autowired
    private DataService dataService;

    @Test
    public void test() throws Exception {
        dataService.batchCreateDataForXtt(1);
    }


}
