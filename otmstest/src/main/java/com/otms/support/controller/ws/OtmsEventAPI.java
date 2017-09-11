package com.otms.support.controller.ws;

import com.otms.support.controller.define.JobSheetRequest;
import com.otms.support.controller.define.JobSheetResponse;
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
@RequestMapping("/event")
public class OtmsEventAPI {

    @Autowired
    private OtmsEventService eventService;

    @PutMapping("/order")
    @InboundLog(APISource.ORDER_EVENT)
    public String orderEvent(@RequestBody OrderEventDto orderEventDto,
                             @RequestParam(value = "mark", required = false) String mark) {
        orderEventDto.setMark(mark);
        eventService.addOrderEvent(orderEventDto);
        return null;
    }

    @PostMapping("/jobSheet")
    @InboundLog(APISource.JOB_SHEET_EVENT)
    public JobSheetResponse jobSheetEvent(@RequestBody JobSheetRequest request,
                                          @RequestParam(value = "mark", required = false) String mark) {
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
}
