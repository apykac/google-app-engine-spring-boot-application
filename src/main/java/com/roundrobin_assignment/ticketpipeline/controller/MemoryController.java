package com.roundrobin_assignment.ticketpipeline.controller;

import com.roundrobin_assignment.ticketpipeline.domain.dto.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memory")
public class MemoryController {

    @GetMapping("/usage")
    public BaseResponse<Long> usage() {
        return new BaseResponse<Long>().setPayload(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }
}
