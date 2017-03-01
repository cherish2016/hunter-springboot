package com.cherish.hunterjpa.service;

import com.cherish.hunterjpa.domain.Hunter;
import com.cherish.hunterjpa.repository.HunterBaseRepository;
import com.cherish.hunterjpa.utils.CollectionUtil;
import com.cherish.hunterjpa.xlsx.DocConsumer;
import com.cherish.hunterjpa.xlsx.DocProducer;
import com.cherish.hunterjpa.xlsx.DocStorage;
import com.cherish.hunterjpa.xlsx.Xlsx2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 简历信息入库操作类
 * Created by cherish on 2017/2/18.
 */
@Component
class HunterOperateService {

    @Autowired
    private HunterBaseRepository baseRepository;

    @Value("${produceThreadNum}")
    private String produceThreadNum;

    @Autowired
    private DocStorage docStorage;

    private static List<File> filesOfDir = new ArrayList<>();

    void saveXlsx2Mysql(String xlsxPath) {
        new Xlsx2Bean() {
            @Override
            public void insertHunter2Mysql(List<Hunter> hunters) {
                baseRepository.save(hunters);
            }
        }.excel2Resumes(xlsxPath);
    }

    void saveDoc2Mysql(String docPath) {
        initFilePathOfDir(new File(docPath));
        int pageSize = filesOfDir.size() / Integer.valueOf(produceThreadNum) + 1;
        List<List<File>> lists = CollectionUtil.splitList(filesOfDir, pageSize);
//        DocStorage docStorage = new DocStorage();
        ExecutorService service = Executors.newCachedThreadPool();
        int i = 1;
        for (List<File> list : lists) {
            service.submit(new DocProducer("producer" + i, docStorage, list) {
                @Override
                public void mergeHunter(Hunter hunter) {
                    Hunter one = baseRepository.findOne(hunter.getPhone());
                    hunter.setRemarks(one.getRemarks());
                    hunter.setUpdateTime(one.getUpdateTime());
                    hunter.setOriginalPosition(one.getOriginalPosition());
                    hunter.setStatus(one.getStatus());
                }
            });
            i++;
        }
        DocConsumer docConsumer = new DocConsumer("consumer", docStorage) {
            @Override
            public void consumer(List<Hunter> hunters) {
                baseRepository.save(hunters);
            }
        };
        DocConsumer docConsumer1 = new DocConsumer("consumer1", docStorage) {
            @Override
            public void consumer(List<Hunter> hunters) {
                baseRepository.save(hunters);
            }
        };
        DocConsumer docConsumer2 = new DocConsumer("consumer2", docStorage) {
            @Override
            public void consumer(List<Hunter> hunters) {
                baseRepository.save(hunters);
            }
        };
        service.submit(docConsumer);
        service.submit(docConsumer1);
        service.submit(docConsumer2);
    }

    private void initFilePathOfDir(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                File[] fileArray = f.listFiles();
                if (fileArray != null) {
                    for (File aFileArray : fileArray) {
                        //递归调用
                        initFilePathOfDir(aFileArray);
                    }
                }
            } else {
                if (f.getName().endsWith(".doc")) {
                    filesOfDir.add(f);
                }
            }
        }
    }
}
