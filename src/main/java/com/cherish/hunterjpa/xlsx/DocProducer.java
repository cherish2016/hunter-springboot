package com.cherish.hunterjpa.xlsx;

import com.cherish.hunterjpa.domain.Hunter;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Doc 生产者
 * Created by Administrator on 2017/2/21.
 */
public class DocProducer implements Runnable {

    private String name;
    private DocStorage docStorage = null;
    private List<File> filesOfDir = new ArrayList<>();

    public DocProducer(String name, DocStorage docStorage, List<File> filesOfDir) {
        this.name = name;
        this.docStorage = docStorage;
        this.filesOfDir = filesOfDir;
    }

    public void run() {
        int size = filesOfDir.size();
        System.out.println(name + "消费: " + size);
        for (File file : filesOfDir) {
            FileInputStream in = null;
            try {
                in = new FileInputStream(file);
                WordExtractor extractor = new WordExtractor(in);
                String[] split = Optional.of(extractor.getText()).map(s -> s.split("\r\n")).orElse(new String[20]);
                Hunter hunter = getHunter(split, file.getPath());
                docStorage.push(hunter);
                if (docStorage.getQueues().size() >= docStorage.getMAX_SIZE()) {
                    System.out.println("暂停生产。。。");
                    Thread.sleep(50);
                }
                size--;
                if (size < 100) {
                    System.out.println(name + "剩余：" + size);
                }
            } catch (Exception e) {
                System.out.println("<<<<<<<<<<<<<<<<<<<<" + file.getPath());
            } finally {
                try {
                    assert in != null;
                    in.close();
                } catch (IOException e) {
                    //
                }
            }
        }

    }

    private Hunter getHunter(String[] split, String filePath) {
        Hunter hunter = new Hunter();
        hunter.setName(split[1].contains("：") ? split[1].split("：")[1] : split[1]);
        hunter.setPhone(split[2].contains("：") ? split[2].split("：")[1] : split[2]);
        hunter.setAddress(split[3]);
        hunter.setStatus(split[4]);
        hunter.setOriginalPosition(split[5]);
        hunter.setUpdateTime(split[8]);
        String remarks = split[19];
        hunter.setRemarks(remarks.length() > 30 ? remarks.substring(0, 30) : remarks);
        hunter.setFileLink(filePath);
        return hunter;
    }

}
