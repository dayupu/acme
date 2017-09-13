package com.otms.support.controller.ws;

import com.otms.support.controller.define.BillingEventResponse;
import com.otms.support.controller.define.JobSheetRequest;
import com.otms.support.controller.define.JobSheetResponse;
import com.otms.support.controller.define.OrderEventRequest;
import com.otms.support.controller.define.OrderEventResponse;
import com.otms.support.kernel.dto.OrderEventDto;
import com.otms.support.kernel.service.OtmsEventService;
import com.otms.support.spring.annotation.InboundLog;
import com.otms.support.supplier.database.enums.APISource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bert on 17-9-11.
 */

@RestController
@RequestMapping("/ws")
public class OtmsEventAPI {

    @Autowired
    private OtmsEventService eventService;

    @PutMapping("/orderTrack")
    @InboundLog(APISource.ORDER_EVENT)
    public OrderEventResponse orderEvent(@RequestBody OrderEventRequest request,
            @RequestParam(value = "CustomCode", required = false) String mark) {
        eventService.addOrderEvent(request, mark);
        OrderEventResponse response = new OrderEventResponse();
        OrderEventResponse.Result result;
        for (OrderEventRequest.Event event : request.getEvents()) {
            result = new OrderEventResponse.Result();
            result.setEventId(event.getEventId());
            result.setErpNumber(event.getErpNumber());
            result.setOrderNumber(event.getOrderNumber());
            result.setResponseCode(0);
            response.getResults().add(result);
        }
        return response;
    }

    @PostMapping("/jobSheetTrack")
    @InboundLog(APISource.JOB_SHEET_EVENT)
    public JobSheetResponse jobSheetEvent(@RequestBody JobSheetRequest request,
            @RequestParam(value = "CustomCode", required = false) String mark) {
        eventService.addJobSheetEvent(request, mark);
        JobSheetResponse response = new JobSheetResponse();
        JobSheetResponse.Result result;
        for (JobSheetRequest.Event event : request.getEvents()) {
            result = new JobSheetResponse.Result();
            result.setEventId(event.getEventId());
            result.setResponseCode(0);
            response.getResults().add(result);
        }
        return response;
    }

    @PutMapping("/billingTrack")
    @InboundLog(APISource.BILLING_EVENT)
    public BillingEventResponse billingEvent(@RequestBody String request,
            @RequestParam(value = "CustomCode", required = false) String mark) {
        eventService.addBillingEvent(request, mark);
        BillingEventResponse response = new BillingEventResponse();
        response.setType(BillingEventResponse.SUCCESS);
        return response;
    }

}
