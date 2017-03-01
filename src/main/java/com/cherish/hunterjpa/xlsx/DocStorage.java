package com.cherish.hunterjpa.xlsx;

import com.cherish.hunterjpa.domain.Hunter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者模式入库
 * Created by Administrator on 2017/2/21.
 */
@Component
public class DocStorage {

    private final int MAX_SIZE = 10;

    private BlockingQueue<List<Hunter>> queues = new LinkedBlockingQueue<>(MAX_SIZE);

    @Value("${produceThreadNum}")
    private String produceThreadNum;

    volatile AtomicInteger atomicInteger = new AtomicInteger(5);

    /**
     * 生产
     *
     * @param hunters DOC
     * @throws InterruptedException
     */
    void push(List<Hunter> hunters) throws InterruptedException {
        queues.put(hunters);
    }

    /**
     * 消费
     *
     * @return hunter
     * @throws InterruptedException
     */
    List<Hunter> pop() throws InterruptedException {
        return queues.take();
    }

    BlockingQueue<List<Hunter>> getQueues() {
        return queues;
    }

    int getMAX_SIZE() {
        return MAX_SIZE;
    }
}
