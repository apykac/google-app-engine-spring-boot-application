package com.roundrobin_assignment.ticketpipeline.flow.element;

import com.roundrobin_assignment.ticketpipeline.dao.QueueDao;
import com.roundrobin_assignment.ticketpipeline.domain.TicketListHandleReport;
import com.roundrobin_assignment.ticketpipeline.util.StringUtils;
import com.roundrobin_assignment.ticketpipeline.util.log.Logger;
import com.roundrobin_assignment.ticketpipeline.util.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetQWorkCompleteZenDeskFlowElement implements FlowElement<TicketListHandleReport, TicketListHandleReport> {
    private static final Logger LOG = LoggerFactory.getLogger(GetQWorkCompleteZenDeskFlowElement.class);

    private final QueueDao queueDao;

    @Autowired
    public GetQWorkCompleteZenDeskFlowElement(QueueDao queueDao) {
        this.queueDao = queueDao;
    }

    @Override
    public FlowElementId getFlowElementId() {
        return FlowElementId.GET_Q_WORK_COMPLETE_ZEN_DESK;
    }

    @Override
    public TicketListHandleReport doFlowStep(TicketListHandleReport income) {
        if (income != null) {
            getQWorkComplete(income);
        }
        return income;
    }

    private void getQWorkComplete(TicketListHandleReport report) {
        try {
            queueDao.getQWorkComplete(
                    report.getGetTask().getId(),
                    report.getProcessTicketCount(),
                    report.getProcessTicketErrors(),
                    report.getTicketList().getNextPage(),
                    report.getStatus().getValue(),
                    report.getErrorCode(),
                    StringUtils.left(report.getErrorMessage(), 255));
        } catch (Exception e) {
            LOG.error("Error during call pGetQWorkComplite(): {}", e::getMessage, () -> e);
            LOG.trace("Error during call pGetQWorkComplite(): entity: {}", () -> report);
        }
    }
}
