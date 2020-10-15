package com.roundrobin_assignment.ticketpipeline.tasks;

import com.roundrobin_assignment.ticketpipeline.config.properties.FlowsProperties;
import com.roundrobin_assignment.ticketpipeline.dao.QueueDao;
import com.roundrobin_assignment.ticketpipeline.domain.QWork;
import com.roundrobin_assignment.ticketpipeline.flow.GetQQueueHandlerFlow;
import com.roundrobin_assignment.ticketpipeline.flow.element.FlowElementStore;
import com.roundrobin_assignment.ticketpipeline.util.log.Logger;
import com.roundrobin_assignment.ticketpipeline.util.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.roundrobin_assignment.ticketpipeline.flow.FlowId.GET_Q_QUEUE_HANDLER;

@Component
@EnableScheduling
public class GetQueueTask implements Task {
    private static final Logger LOG = LoggerFactory.getLogger(GetQueueTask.class);

    private final Queue<QWork> qWorkQueue = new ConcurrentLinkedDeque<>();

    private final QueueDao queueDao;
    private final FlowElementStore flowElementStore;
    private final WorkedFlowManager workedFlowManager;
    private final ExecutorService executorService;

    @Autowired
    public GetQueueTask(QueueDao queueDao, FlowElementStore flowElementStore, WorkedFlowManager workedFlowManager, FlowsProperties flowsProperties) {
        this.queueDao = queueDao;
        this.flowElementStore = flowElementStore;
        this.workedFlowManager = workedFlowManager;
        executorService = Executors.newFixedThreadPool(flowsProperties.getFlowsProperties().get(GET_Q_QUEUE_HANDLER).getThreadCount());
    }

//    @Scheduled(fixedDelayString = "${app.task.get-queue.fixed-delay}")
    public void run() {
        LOG.debug("Run GetQueueTask");
        QWork qWork;
        while (!workedFlowManager.isMax(GET_Q_QUEUE_HANDLER) && (qWork = getQWork()) != null) {
            executorService.execute(new GetQQueueHandlerFlow(flowElementStore, workedFlowManager, qWork));
        }
    }

    private QWork getQWork() {
        QWork qWork = qWorkQueue.poll();
        if (qWork == null) {
            fillQWorkQueue();
            qWork = qWorkQueue.poll();
        }
        return qWork;
    }

    private void fillQWorkQueue() {
        List<QWork> qWorkList = queueDao.getTasks();
        if (!CollectionUtils.isEmpty(qWorkList)) {
            qWorkQueue.addAll(qWorkList);
        }
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }
}
