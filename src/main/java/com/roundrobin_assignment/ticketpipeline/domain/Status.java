package com.roundrobin_assignment.ticketpipeline.domain;

public enum Status {
    NEW,
    OPEN,
    PENDING,
    HOLD,
    SOLVED,
    CLOSED,
    DELETED;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}