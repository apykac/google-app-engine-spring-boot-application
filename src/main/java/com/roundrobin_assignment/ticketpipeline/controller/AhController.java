package com.roundrobin_assignment.ticketpipeline.controller;

import com.roundrobin_assignment.ticketpipeline.domain.dto.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/_ah")
public class AhController {
    private static final Logger LOG = LoggerFactory.getLogger(AhController.class);
    //    private final FlowsManager flowsManager;
    private final ApplicationContext applicationContext;

//    public AhController(FlowsManager flowsManager, ApplicationContext applicationContext) {
//        this.flowsManager = flowsManager;
//        this.applicationContext = applicationContext;
//    }


    public AhController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @GetMapping({"/start"})
    public BaseResponse<String> start() {
        LOG.info("/_ah/start was called");
        return new BaseResponse<String>().setPayload("Started");
    }

    @GetMapping({"/stop"})
    public BaseResponse<String> stop() {
        LOG.info("/_ah/stop was called, starting shutdown application...");
//            flowsManager.stopManagement();
        new Thread(() -> {
            try {
                Thread.sleep(1000L);
                int count = 0;
                while (((ConfigurableApplicationContext) applicationContext).isActive() && count++ != 10) {
                    Thread.sleep(1000L);
                }
                if (count == 10) {
                    throw new IllegalStateException("Can't stop context");
                }
                System.exit(0);
            } catch (Exception e) {
                LOG.error("Can't stop context: {}", e.getMessage(), e);
            }
        }).start();

        return new BaseResponse<String>().setPayload("application context is stopping...");
    }
}
