package com.otms.support.controller.ws;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bert on 17-9-11.
 */

@RestController
@RequestMapping("/event")
public class OtmsEventAPI {

    @PutMapping("/order")
    public String orderEvent(@RequestBody String body) {

        return null;
    }
}
