package com.roundrobin_assignment.ticketpipeline.flow;

import com.roundrobin_assignment.ticketpipeline.domain.Cleanable;

public interface Flow extends Runnable {
    FlowId getFlowId();

    default void clean(Cleanable... cleanables) {
        for (int i = 0; i < cleanables.length; i++) {
            cleanables[i].clean();
            cleanables[i] = null;
        }
    }
}
