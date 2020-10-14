package com.roundrobin_assignment.ticketpipeline.flow.element;

import com.roundrobin_assignment.ticketpipeline.domain.QWork;
import com.roundrobin_assignment.ticketpipeline.domain.TaskStatus;
import com.roundrobin_assignment.ticketpipeline.domain.TicketList;
import com.roundrobin_assignment.ticketpipeline.domain.TicketListHandleReport;
import com.roundrobin_assignment.ticketpipeline.util.CryptUtil;
import com.roundrobin_assignment.ticketpipeline.util.log.Logger;
import com.roundrobin_assignment.ticketpipeline.util.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
public class GetTicketListZenDeskFlowElement implements FlowElement<QWork, TicketListHandleReport> {
    private static final Logger LOG = LoggerFactory.getLogger(GetTicketListZenDeskFlowElement.class);

    private final RestTemplate zenDeskRestTemplate;

    @Autowired
    public GetTicketListZenDeskFlowElement(RestTemplate zenDeskRestTemplate) {
        this.zenDeskRestTemplate = zenDeskRestTemplate;
    }

    @Override
    public FlowElementId getFlowElementId() {
        return FlowElementId.GET_TICKET_LIST_ZEN_DESK;
    }

    @Override
    public TicketListHandleReport doFlowStep(QWork qWork) {
        return qWork == null ? null : getTicketList(qWork);
    }

    private TicketListHandleReport getTicketList(QWork qWork) {
        String url = qWork.getUrl();
        try {
            String user = qWork.getUser();
            String pwd = CryptUtil.decode(qWork.getPwd());
            HttpEntity<String> entity = getHttpEntity(user, pwd);
            ResponseEntity<TicketList> response = zenDeskRestTemplate.exchange(url, HttpMethod.GET, entity, TicketList.class);
            return fromResponse(response, qWork);
        } catch (Exception e) {
            LOG.error("Error during call ZenDesk url: {}, cause: {}", () -> url, e::getMessage, () -> e);
            return fromException(e, qWork);
        }
    }

    private HttpEntity<String> getHttpEntity(String user, String pwd) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(user, pwd, StandardCharsets.UTF_8);
        return new HttpEntity<>(headers);
    }

    private TicketListHandleReport fromResponse(ResponseEntity<TicketList> response, QWork qWork) {
        TicketListHandleReport result = new TicketListHandleReport().setGetTask(qWork);
        if (response.getStatusCode() != HttpStatus.OK) {
            return result
                    .setErrorCode(response.getStatusCodeValue())
                    .setStatus(TaskStatus.ERROR);
        }
        return result.setTicketList(response.getBody());
    }

    private TicketListHandleReport fromException(Exception e, QWork qWork) {
        return new TicketListHandleReport()
                .setGetTask(qWork)
                .setStatus(TaskStatus.ERROR)
                .setErrorMessage(e.getMessage());
    }
}
