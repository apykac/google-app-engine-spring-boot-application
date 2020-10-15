package com.roundrobin_assignment.ticketpipeline.controller;

import com.roundrobin_assignment.ticketpipeline.domain.dto.BaseResponse;
import com.roundrobin_assignment.ticketpipeline.tasks.GetQueueTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {
    private final GetQueueTask getQueueTask;

    public MainController(GetQueueTask getQueueTask) {
        this.getQueueTask = getQueueTask;
    }

    @GetMapping("/runGetTask")
    public BaseResponse<String> getTask() {
        getQueueTask.run();
        return new BaseResponse<String>().setPayload("Done");
    }


    //    private final FlowsManager flowsManager;
//
//    public MainController(FlowsManager flowsManager) {
//        this.flowsManager = flowsManager;
//    }
//
//    @GetMapping({"/isWorked"})
//    public BaseResponse<String> info() {
//        return new BaseResponse<String>().setPayload("application is worked");
//    }
//
//    @GetMapping({"/set/{flowId}/batch-size/{size}"})
//    public BaseResponse<String> setBatchSize(@PathVariable("flowId") FlowId flowId, @PathVariable("size") int batchSize) {
//        try {
//            flowsManager.setBatchSize(flowId, batchSize);
//            return new BaseResponse<String>().setPayload("batch size for " + flowId + " set to size " + batchSize);
//        } catch (Exception var4) {
//            return new BaseResponse<String>().setPayload("Problem during set batch size").setException(var4);
//        }
//    }
//
//    @GetMapping({"/set/{flowId}/max-worked-floes/{MaxWorkedFlows}"})
//    public BaseResponse<String> setMaxWorkedFlows(@PathVariable("flowId") FlowId flowId, @PathVariable("MaxWorkedFlows") int maxWorkedFlows) {
//        try {
//            flowsManager.setMaxWorkedFlows(flowId, maxWorkedFlows);
//            return new BaseResponse<String>()
//                    .setPayload("max worked flows for " + flowId + " set to size " + maxWorkedFlows);
//        } catch (Exception var4) {
//            return new BaseResponse<String>().setPayload("Problem during set max worked flows").setException(var4);
//        }
//    }
//
//    @GetMapping({"/set/idle/{idle}"})
//    public BaseResponse<String> setMaxWorkedFlows(@PathVariable("idle") int idle) {
//        try {
//            FlowsManager.setIdle((long)idle);
//            return new BaseResponse<String>().setPayload("idle set to size " + idle);
//        } catch (Exception var3) {
//            return new BaseResponse<String>().setPayload("Problem during set idle").setException(var3);
//        }
//    }
}