package com.cherish.hunterjpa.service;

import com.cherish.hunterjpa.domain.Hunter;
import com.cherish.hunterjpa.repository.HunterBaseRepository;
import com.cherish.hunterjpa.utils.CollectionUtil;
import com.cherish.hunterjpa.xlsx.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 简历信息入库操作类
 * Created by cherish on 2017/2/18.
 */
@Component
public class HunterOperateService {

    @Autowired
    private HunterBaseRepository baseRepository;

    public void saveXlsx2Mysql(String xlsxPath) {
        new Xlsx2Bean() {
            @Override
            public void insertHunter2Mysql(List<Hunter> hunters) {
                baseRepository.save(hunters);
            }
        }.excel2Resumes(xlsxPath);
    }

    public void saveDoc2Mysql(String docPath) {
        List<File> filesOfDir = Doc2Bean.getFilesOfDir(docPath);
        int pageSize = filesOfDir.size() / 5 + 1;
        List<List<File>> lists = CollectionUtil.splitList(filesOfDir, pageSize);
        DocStorage docStorage = new DocStorage();

        ExecutorService service = Executors.newCachedThreadPool();
        int i = 1;
        for (List<File> list : lists) {
            service.submit(new DocProducer("producer" + i, docStorage, list));
            i++;
        }
        DocConsumer docConsumer = new DocConsumer("consumer", docStorage) {
            @Override
            public void consumer(List<Hunter> hunters) {
                for (Hunter hunter : hunters) {
                    baseRepository.save(hunter);
                }
            }
        };
        DocConsumer docConsumer1 = new DocConsumer("consumer1", docStorage) {
            @Override
            public void consumer(List<Hunter> hunters) {
                for (Hunter hunter : hunters) {
                    baseRepository.save(hunter);
                }
            }
        };
        DocConsumer docConsumer2 = new DocConsumer("consumer2", docStorage) {
            @Override
            public void consumer(List<Hunter> hunters) {
                for (Hunter hunter : hunters) {
                    baseRepository.save(hunter);
                }
            }
        };
        service.submit(docConsumer);
        service.submit(docConsumer1);
        service.submit(docConsumer2);
    }

}
