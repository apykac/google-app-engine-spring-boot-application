package com.roundrobin_assignment.ticketpipeline;

import com.roundrobin_assignment.ticketpipeline.util.log.Logger;
import com.roundrobin_assignment.ticketpipeline.util.log.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
public class TicketPipelineApplication {
    private static final Logger LOG = LoggerFactory.getLogger(TicketPipelineApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TicketPipelineApplication.class, args);
    }

    @PostConstruct
    public void initApp() {
        LOG.info("Application ticket-pipeline is starting...");
        LOG.info("MEMORY CONSUMING {}", () -> Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }

    @PreDestroy
    public void destroyApp() {
        LOG.info("Application ticket-pipeline is stopped");
    }
}
