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
        for (File file : filesOfDir) {
            FileInputStream in = null;
            String path = file.getPath();
            try {
                in = new FileInputStream(file);
                WordExtractor extractor = new WordExtractor(in);
                Hunter hunter = getHunter(path, extractor);
                docStorage.push(hunter);
                if (docStorage.getQueues().size() >= docStorage.getMAX_SIZE()) {
                    System.out.println("暂停生产???????????????????????????????????????????????????????????????????????????????????");
                    Thread.sleep(5);
                }
                size--;
                if (size < 100) {
                    System.out.println(name + "剩余：" + size);
                }
            } catch (Exception e) {
                System.out.println("<<<<<<<<<<<<<<<<<<<<" + path);
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

    private Hunter getHunter(String path, WordExtractor extractor) {
        String text = extractor.getText();
//        String[] split = Optional.of(text).map(s -> s.split("\r\n")).orElse(new String[20]);
        Hunter hunter = new Hunter();
        hunter.setEducation(getEducaion(text));
        String phone = path.substring(path.indexOf("_") + 1, path.lastIndexOf("_"));
        hunter.setPhone(phone);
        hunter.setFileLink(path);
        hunter.setSchool(getSchoolName(text));
        hunter.setWorkingYears(getWorkAge(text));
/*        try {
           *//* for (String s : split) {
                if (s.contains("大学（")) {
                    hunter.setSchool(s.substring(0, s.indexOf("（")));
                }
                if (s.contains("工作年限：")) {
                    String year = Optional.ofNullable(s.split("：")[1].split(" ")[0]).orElse("未知");
                    hunter.setWorkingYears(year);
                }
            }*//*
        } catch (Exception e) {
            return hunter;
        }*/
        return hunter;
    }

    private String getEducaion(String text) {
        if (text.contains("博士")) return "博士";
        if (text.contains("硕士")) return "硕士";
        if (text.contains("本科")) return "本科";
        if (text.contains("大专") || text.contains("专科")) return "专科";
        return "未知";
    }

    private String getSchoolName(String text) {
        if (text.contains("大学（")) {
            int endIndex = text.indexOf("大学（");
            String substring = text.substring(endIndex - 8, endIndex + 2);
            return substring.substring(substring.indexOf("\n"));
        }
        return "未知";
    }

    private String getWorkAge(String text) {
        if (text.contains("工作年限：")) {
            int beginIndex = text.indexOf("工作年限：") + 5;
            return text.substring(beginIndex, beginIndex + 4);
        }
        return "未知";
    }

}
