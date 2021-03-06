package com.roundrobin_assignment.ticketpipeline.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketListHandleReport implements Cleanable {
    private TicketList ticketList;
    private QWork qWork;
    private int processTicketCount;
    private int processTicketErrors;
    private TaskStatus status; //tinyint
    private Integer errorCode;
    private String errorMessage; //varchar(255)

    public TicketList getTicketList() {
        return ticketList;
    }

    public QWork getGetTask() {
        return qWork;
    }

    public int getProcessTicketCount() {
        return processTicketCount;
    }

    public int getProcessTicketErrors() {
        return processTicketErrors;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public TicketListHandleReport setTicketList(TicketList ticketList) {
        this.ticketList = ticketList;
        return this;
    }

    public TicketListHandleReport setGetTask(QWork qWork) {
        this.qWork = qWork;
        return this;
    }

    public TicketListHandleReport setProcessTicketCount(int processTicketCount) {
        this.processTicketCount = processTicketCount;
        return this;
    }

    public TicketListHandleReport setProcessTicketErrors(int processTicketErrors) {
        this.processTicketErrors = processTicketErrors;
        return this;
    }

    public TicketListHandleReport setStatus(TaskStatus status) {
        this.status = status;
        return this;
    }

    public TicketListHandleReport setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public TicketListHandleReport setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketListHandleReport that = (TicketListHandleReport) o;
        return processTicketCount == that.processTicketCount &&
                processTicketErrors == that.processTicketErrors &&
                Objects.equals(ticketList, that.ticketList) &&
                Objects.equals(qWork, that.qWork) &&
                status == that.status &&
                Objects.equals(errorCode, that.errorCode) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketList, qWork, processTicketCount, processTicketErrors, status, errorCode, errorMessage);
    }

    @Override
    public void clean() {
        ticketList.clean();
        ticketList = null;
        qWork.clean();
        qWork = null;
        status = null;
        errorCode = null;
        errorMessage = null;
    }

    @Override
    public String toString() {
        return "TicketListHandleReport{" +
                "ticketList=" + ticketList +
                ", getTask=" + qWork +
                ", processTicketCount=" + processTicketCount +
                ", processTicketErrors=" + processTicketErrors +
                ", status=" + status +
                ", errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
