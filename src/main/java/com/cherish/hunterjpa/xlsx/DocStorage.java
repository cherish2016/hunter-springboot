package com.cherish.hunterjpa.xlsx;

import com.cherish.hunterjpa.domain.Hunter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 生产者消费者模式入库
 * Created by Administrator on 2017/2/21.
 */
public class DocStorage {

    private final int MAX_SIZE = 500;

    private BlockingQueue<Hunter> queues = new LinkedBlockingQueue<>(MAX_SIZE);

    /**
     * 生产
     *
     * @param hunter DOC
     * @throws InterruptedException
     */
    public void push(Hunter hunter) throws InterruptedException {
        queues.put(hunter);
    }

    /**
     * 消费
     *
     * @return hunter
     * @throws InterruptedException
     */
    public Hunter pop() throws InterruptedException {
        return queues.take();
    }

    public BlockingQueue<Hunter> getQueues() {
        return queues;
    }

    public int getMAX_SIZE() {
        return MAX_SIZE;
    }
}
