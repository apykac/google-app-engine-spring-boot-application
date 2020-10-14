package com.roundrobin_assignment.ticketpipeline.dao;

import com.roundrobin_assignment.ticketpipeline.domain.QWork;
import com.roundrobin_assignment.ticketpipeline.util.log.Logger;
import com.roundrobin_assignment.ticketpipeline.util.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QueueDaoImpl implements QueueDao {
    private static final Logger LOG = LoggerFactory.getLogger(QueueDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final Converter converter;

    @Autowired
    public QueueDaoImpl(JdbcTemplate jdbcTemplate, Converter converter) {
        this.jdbcTemplate = jdbcTemplate;
        this.converter = converter;
    }

    @Override
    public List<QWork> getTasks() {
        try {
            LOG.trace("Start call pGetQWork()");

            List<QWork> QWorkList = jdbcTemplate.query("call pGetQWork()", (rs, rowNum) -> {
                try {
                    return new QWork()
                            .setId(rs.getObject("id", BigInteger.class))
                            .setSiteId(rs.getObject("site_id", Integer.class))
                            .setQueueId(rs.getObject("queue_id", Integer.class))
                            .setGetId(rs.getObject("get_id", BigInteger.class))
                            .setUrl(rs.getObject("url", String.class))
                            .setAllowReassign(rs.getObject("allow_reassign", Boolean.class))
                            .setProcSpeed(rs.getObject("proc_speed", Integer.class))
                            .setProcStartTime(rs.getObject("proc_start_time", LocalDateTime.class))
                            .setProcType(rs.getObject("proc_type", Integer.class))
                            .setUser(rs.getObject("user", String.class))
                            .setPwd(rs.getObject("pwd", String.class))
                            .setGetQType(rs.getObject("get_q_type", Integer.class));
                } catch (Exception e) {
                    throw new SQLException("Can't parse date cause: " + e.getMessage(), e);
                }
            });
            LOG.trace("Result of pGetQWork(): {}", () -> QWorkList.stream().map(String::valueOf).collect(Collectors.joining(", ")));
            return QWorkList;
        } catch (Exception e) {
            LOG.error("Error during call pGetQWork(): {}", e::getMessage, () -> e);
            return Collections.emptyList();
        }
    }

    @Override
    public void processTicket(BigInteger getId,
                              LocalDateTime dueTime,
                              Integer siteId,
                              Integer queueId,
                              Long ticketId,
                              String ticketTags,
                              Long ticketGroupId,
                              Long ticketRequesterId,
                              Long ticketAssigneeId,
                              String ticketStatus,
                              Integer procType,
                              Integer getQType) {
        LOG.trace("Start call pProcessTicket(), with params: getId={}, dueTime={}, siteId={}, queueId={}, ticketId={}, " +
                        "ticketTags={}, ticketGroupId={}, ticketRequesterId={}, ticketAssigneeId={}, ticketStatus={}, procType={}, getQType={}",
                () -> getId, () -> dueTime, () -> siteId, () -> queueId, () -> ticketId, () -> ticketTags, () -> ticketGroupId,
                () -> ticketRequesterId, () -> ticketAssigneeId, () -> ticketStatus, () -> procType, () -> getQType);

        jdbcTemplate.update("call pProcessTicket(?,?,?,?,?,?,?,?,?,?,?,?)",
                getId, dueTime, siteId, queueId, ticketId, ticketTags, ticketGroupId, ticketRequesterId, ticketAssigneeId, ticketStatus, procType, getQType);
    }

    @Override
    public void getQWorkComplete(BigInteger id,
                                 int cnt,
                                 int cntErr,
                                 String nextPageUrl,
                                 int status,
                                 Integer errorCode,
                                 String errorMsg) {
        LOG.trace("Start call pGetQWorkComplite(), with params: id={}, cnt={}, cntErr={}, nextPageUrl={}, status={}, errorCode={}, errorMsg={}",
                () -> id, () -> cnt, () -> cntErr, () -> nextPageUrl, () -> status, () -> errorCode, () -> errorMsg);

        jdbcTemplate.update("call pGetQWorkComplite(?,?,?,?,?,?,?)",
                id, cnt, cntErr, nextPageUrl, status, errorCode, errorMsg);
    }
}
