package com.cherish.hunterjpa.service;

import com.cherish.hunterjpa.domain.Hunter;
import com.cherish.hunterjpa.repository.HunterBaseRepository;
import com.cherish.hunterjpa.utils.CollectionUtil;
import com.cherish.hunterjpa.xlsx.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 查询简历信息处理类
 * Created by cherish on 2017/2/18.
 */
@Component
public class HunterOperateService {

    @Autowired
    private HunterBaseRepository baseRepository;

    public void saveXlsx2Mysql(String xlsxPath) {
        List<Hunter> hunters = new Xlsx2Bean().excel2ResumesOfJson(xlsxPath);
        baseRepository.save(hunters);
    }

    public void saveDoc2Mysql(String docPath) {
        List<File> filesOfDir = Doc2Bean.getFilesOfDir(docPath);
        int pageSize = filesOfDir.size() / 5 + 1;
        List<List<File>> lists = CollectionUtil.splitList(filesOfDir, pageSize);
        DocStorage docStorage = new DocStorage();

        ExecutorService service = Executors.newCachedThreadPool();
        final String[] producerName = {"p"};
        lists.forEach(files -> {
            producerName[0] += "q";
            service.submit(new DocProducer(producerName[0], docStorage, files));
        });
        DocConsumer docConsumer = new DocConsumer("consumer", docStorage) {
            @Override
            public void consumer(BlockingQueue<Hunter> hunters) {
                baseRepository.save(hunters);
                hunters.clear();
            }
        };
        service.submit(docConsumer);
    }

}
