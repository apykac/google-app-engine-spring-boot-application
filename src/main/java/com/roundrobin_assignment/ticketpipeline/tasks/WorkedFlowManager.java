package com.roundrobin_assignment.ticketpipeline.tasks;

import com.roundrobin_assignment.ticketpipeline.config.properties.FlowsProperties;
import com.roundrobin_assignment.ticketpipeline.flow.FlowId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class WorkedFlowManager {
    private final Map<FlowId, FlowThreadContainer> workedFlows = new EnumMap<>(FlowId.class);

    @Autowired
    public WorkedFlowManager(FlowsProperties flowsProperties) {
        flowsProperties.getFlowsProperties().forEach(((flowId, flowProperties) ->
                workedFlows.put(flowId, new FlowThreadContainer(flowProperties.getThreadCount()))));
    }

    public WorkedFlowManager addFlow(FlowId flowId) {
        FlowThreadContainer container = workedFlows.get(flowId);
        if (container != null) {
            container.workedFlows.getAndIncrement();
        }
        return this;
    }

    public WorkedFlowManager removeFlow(FlowId flowId) {
        FlowThreadContainer container = workedFlows.get(flowId);
        if (container != null) {
            container.workedFlows.getAndDecrement();
        }
        return this;
    }

    public boolean isMax(FlowId flowId) {
        FlowThreadContainer container = workedFlows.get(flowId);
        if (container != null) {
            return container.isMax();
        } else {
            return true;
        }
    }

    private static class FlowThreadContainer {
        private final AtomicInteger workedFlows = new AtomicInteger(0);
        private final int max;

        public FlowThreadContainer(int max) {
            this.max = max;
        }

        public boolean isMax() {
            return max <= workedFlows.get();
        }
    }
}
