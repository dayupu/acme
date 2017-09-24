package com.manage.kernel.core;

import com.manage.kernel.jpa.news.entity.EventPush;
import com.manage.kernel.jpa.news.repository.EventPushRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bert on 2017/9/24.
 */
@Service
public class EventPushService {

    @Autowired
    private EventPushRepo eventPushRepo;

    public void push() {
        Iterable<EventPush> events = eventPushRepo.findAll();
        for (EventPush event : events) {
            System.out.println(event.getRequestBody());
        }
    }

    private void httpRequest(){

    }
}
