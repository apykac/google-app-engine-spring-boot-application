package com.roundrobin_assignment.ticketpipeline.config.properties;

import com.roundrobin_assignment.ticketpipeline.flow.FlowId;

import java.util.Map;

public class FlowsProperties {
    private Map<FlowId, FlowProperties> map;

    public Map<FlowId, FlowProperties> getFlowsProperties() {
        return map;
    }

    public void setFlowsProperties(Map<FlowId, FlowProperties> map) {
        this.map = map;
    }

    public static class FlowProperties {
        private int threadCount;
        private int batchSize;

        public int getThreadCount() {
            return threadCount;
        }

        public void setThreadCount(int threadCount) {
            this.threadCount = threadCount;
        }

        public int getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(int batchSize) {
            this.batchSize = batchSize;
        }
    }
}
