package com.navercorp.pinpoint.web.dao.hbase;

import com.navercorp.pinpoint.common.server.bo.SpanBo;
import com.navercorp.pinpoint.web.dao.TraceDao;
import com.navercorp.pinpoint.web.vo.TransactionId;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author Woonduk Kang(emeroad)
 */
public class HbaseTraceCompatibilityDao implements TraceDao {

    private final TraceDao master;
    private final TraceDao slave;

    public HbaseTraceCompatibilityDao(TraceDao master, TraceDao slave) {
        if (master == null) {
            throw new NullPointerException("master must not be null");
        }
        if (slave == null) {
            throw new NullPointerException("slave must not be null");
        }

        this.master = master;
        this.slave = slave;
    }

    @Override
    public List<SpanBo> selectSpan(TransactionId transactionId) {
        List<SpanBo> spanBos = this.master.selectSpan(transactionId);
        if (CollectionUtils.isNotEmpty(spanBos)) {
            return spanBos;
        }

        return slave.selectSpan(transactionId);
    }

    @Override
    public List<SpanBo> selectSpanAndAnnotation(TransactionId transactionId) {
        List<SpanBo> spanBos = this.master.selectSpanAndAnnotation(transactionId);
        if (CollectionUtils.isNotEmpty(spanBos)) {
            return spanBos;
        }

        return slave.selectSpanAndAnnotation(transactionId);
    }

    @Override
    public List<List<SpanBo>> selectSpans(List<TransactionId> transactionIdList) {
        List<List<SpanBo>> spanBos = this.master.selectSpans(transactionIdList);
        if (CollectionUtils.isNotEmpty(spanBos)) {
            return spanBos;
        }

        return slave.selectSpans(transactionIdList);
    }

    @Override
    public List<List<SpanBo>> selectAllSpans(Collection<TransactionId> transactionIdList) {
        List<List<SpanBo>> spanBos = this.master.selectAllSpans(transactionIdList);
        if (CollectionUtils.isNotEmpty(spanBos)) {
            return spanBos;
        }

        return slave.selectAllSpans(transactionIdList);
    }

    @Override
    public List<SpanBo> selectSpans(TransactionId transactionId) {
        List<SpanBo> spanBos = this.master.selectSpans(transactionId);
        if (CollectionUtils.isNotEmpty(spanBos)) {
            return spanBos;
        }

        return slave.selectSpans(transactionId);
    }
}
