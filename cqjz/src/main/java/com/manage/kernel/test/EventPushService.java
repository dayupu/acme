package com.manage.kernel.test;

import com.manage.kernel.jpa.news.entity.EventPush;
import com.manage.kernel.jpa.news.repository.EventPushRepo;
import com.manage.kernel.test.EventPushUtil;
import static com.manage.kernel.test.EventPushUtil.sendPostRequest;
import com.manage.kernel.test.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bert on 2017/9/24.
 */
@Service
public class EventPushService {

    private static final String QA_URL = "https://esb.qa.otms.cn/bestseller/discrepancy/push/internal";
    private static final String PROD_URL = "https://esb.otms.cn/bestseller/discrepancy/push/internal";

    @Autowired
    private EventPushRepo eventPushRepo;

    @Transactional
    public void push() {
        Iterable<EventPush> events = eventPushRepo.queryEventWithNoPush();
        for (EventPush event : events) {
            System.out.println(event.getRequestBody());
            EventResponse response = EventPushUtil
                    .sendPostRequest(event.getRequestBody(), PROD_URL, "otms", "otms", false);
            System.out.println(response.getEntityStr());
            event.setEventPushed(true);
            eventPushRepo.save(event);
        }

    }
}
