package com.roundrobin_assignment.ticketpipeline.flow.element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FlowElementStore {
    private final Map<FlowElementId, FlowElement<?, ?>> flowElementMap;

    @Autowired
    public FlowElementStore(List<FlowElement<?, ?>> flowElements) {
        this.flowElementMap = flowElements.stream()
                .collect(() -> new HashMap<>((int) Math.min(flowElements.size() / 0.75, 16), 1),
                        (m, fe) -> m.put(fe.getFlowElementId(), fe),
                        HashMap::putAll);
    }

    @SuppressWarnings("unchecked")
    public <I, O> FlowElement<I, O> getFlowElement(@NonNull FlowElementId id) {
        Assert.notNull(id, "FlowElementId cant be null");
        try {
            return (FlowElement<I, O>) Optional.ofNullable(flowElementMap.get(id))
                    .orElseThrow(() -> new IllegalArgumentException("Can't find Flow Element by id"));
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Not right generic types");
        }
    }
}
