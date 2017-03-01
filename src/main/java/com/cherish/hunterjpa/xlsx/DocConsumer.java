package com.cherish.hunterjpa.xlsx;

import com.cherish.hunterjpa.domain.Hunter;

import java.util.List;

/**
 * doc 转hunter对象只有入库
 * Created by Administrator on 2017/2/21.
 */
public abstract class DocConsumer implements Runnable {

    private String name;
    private DocStorage docStorage = null;

    protected DocConsumer(String name, DocStorage docStorage) {
        this.name = name;
        this.docStorage = docStorage;
    }

    public void run() {
        try {
            while (docStorage.atomicInteger.get() > 0) {
                if (docStorage.getQueues().size() == 0) {
                    Thread.sleep(100);
                } else {
                    consumer(docStorage.pop());
                }
            }
            System.out.println("数据录入结束");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public abstract void consumer(List<Hunter> hunters);
}
