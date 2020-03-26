package com.lvshen.demo.redis.delayqueue.utils;


import com.lvshen.demo.redis.delayqueue.constants.JobProcess;
import com.lvshen.demo.redis.delayqueue.constants.JobStatus;

/**
 *
 * @author daify
 * @date 2019-07-26 16:41
 **/
public class JobStatusUtils {

    /**
     * 获得下一步节点
     * @param status
     * @param process
     * @return
     */
    public static JobStatus getNext(JobStatus status, JobProcess process) {
        switch (process) {
            case PUT:
                return put(status);
            case FINISH:
                return finish(status);
            case DELETE:
                return delete(status);
            case TTR:
                return ttr(status);
            case RESERVE:
                return reserve(status);
            case PUT_DELAY:
                return putDelay(status);
            case TIME_PASS:
                return timePass(status);
            default:
                return null;
        }
    }


    private static JobStatus timePass(JobStatus status) {
        if (JobStatus.DELAY == status) {
            return JobStatus.READY;
        }
        return null;
    }

    private static JobStatus putDelay(JobStatus status) {
        if (status == null) {
            return JobStatus.DELAY;
        }
        return null;
    }

    private static JobStatus reserve(JobStatus status) {
        if (JobStatus.READY == status) {
            return JobStatus.RESERVED;
        }
        return null;
    }

    private static JobStatus ttr(JobStatus status) {
        if (JobStatus.RESERVED == status) {
            return JobStatus.DELAY;
        }
        return null;
    }

    private static JobStatus finish(JobStatus status) {
        if (JobStatus.RESERVED == status) {
            return JobStatus.DELETED;
        }
        return null;
    }

    private static JobStatus delete(JobStatus status) {
        return JobStatus.DELETED;
    }

    private static JobStatus put(JobStatus status) {
        if (status == null) {
            return JobStatus.READY;
        }
        return null;
    }
    
    
}
