package com.roundrobin_assignment.ticketpipeline.domain;

public enum Type {
    PROBLEM,
    INCIDENT,
    QUESTION,
    TASK;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}