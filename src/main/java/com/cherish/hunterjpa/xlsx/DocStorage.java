package com.cherish.hunterjpa.xlsx;

import com.cherish.hunterjpa.domain.Hunter;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 生产者消费者模式入库
 * Created by Administrator on 2017/2/21.
 */
public class DocStorage {

    private final int MAX_SIZE = 10;

    private BlockingQueue<List<Hunter>> queues = new LinkedBlockingQueue<>(MAX_SIZE);

    public boolean isWorking = true;

    /**
     * 生产
     *
     * @param hunters DOC
     * @throws InterruptedException
     */
    public void push(List<Hunter> hunters) throws InterruptedException {
        queues.put(hunters);
    }

    /**
     * 消费
     *
     * @return hunter
     * @throws InterruptedException
     */
    public List<Hunter> pop() throws InterruptedException {
        return queues.take();
    }

    public BlockingQueue<List<Hunter>> getQueues() {
        return queues;
    }

    public int getMAX_SIZE() {
        return MAX_SIZE;
    }
}
