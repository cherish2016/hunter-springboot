package com.cherish.hunterjpa.xlsx;

import com.cherish.hunterjpa.domain.Hunter;

import java.util.concurrent.BlockingQueue;

/**
 * doc 转hunter对象只有入库
 * Created by Administrator on 2017/2/21.
 */
public abstract class DocConsumer implements Runnable {

    private String name;
    private DocStorage docStorage = null;

    public DocConsumer(String name, DocStorage docStorage) {
        this.name = name;
        this.docStorage = docStorage;
    }

    public void run() {
        try {
            while (true) {
                consumer(docStorage.getQueues());
                System.out.println("===============");
                if (docStorage.getQueues().size() == 0) {
                    Thread.sleep(50);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public abstract void consumer(BlockingQueue<Hunter> hunter);
}