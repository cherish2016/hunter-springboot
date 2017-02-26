package com.cherish.hunterjpa.xlsx;

import com.cherish.hunterjpa.domain.Hunter;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Doc 生产者
 * Created by Administrator on 2017/2/21.
 */
public abstract class DocProducer implements Runnable {

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
        ArrayList<Hunter> hunters = new ArrayList<>();
        for (File file : filesOfDir) {
            FileInputStream in = null;
            try {
                in = new FileInputStream(file);
                WordExtractor extractor = new WordExtractor(in);
                if (docStorage.getQueues().size() >= docStorage.getMAX_SIZE()) {
                    System.out.println("暂停生产???????????????????????????????????" + file.getPath());
                    Thread.sleep(5);
                }
                if (hunters.size() <= 100) {
                    hunters.add(getHunter(file, extractor));
                } else {
                    docStorage.push((List<Hunter>) hunters.clone());
                    hunters.clear();
                }
                size--;
                if (size < 100) {
                    System.out.println(this.name + "剩余：" + size);
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

    private Hunter getHunter(File fileName, WordExtractor extractor) {
        Hunter hunter = new Hunter();
        try {
            String[] infos = fileName.getName().split("_");
            String text = extractor.getText();
            hunter.setName(infos[0]);
            hunter.setPhone(infos[1].replace(" ", ""));
            hunter.setAddress(infos[2].replace(".doc", ""));
            hunter.setEducation(getEducaion(text));
            hunter.setFileLink(fileName.getPath());
            hunter.setSchool(getSchoolName(text));
            hunter.setWorkingYears(getWorkAge(text));
            mergeHunter(hunter);
        } catch (Exception e) {
            System.out.println("sssssss");
        }
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
        int endIndex;
        if (text.contains("大学（")) {
            endIndex = text.indexOf("大学（");
            String substring = text.substring(endIndex - 8, endIndex + 2);
            int beginIndex1 = substring.indexOf("\n");
            int beginIndex2 = substring.indexOf(" ");
            int beginIndex3 = substring.indexOf("\t");
            int beginIndex = beginIndex1 > -1 ? beginIndex1
                    : beginIndex2 > -1 ? beginIndex2
                    : beginIndex3 > -1 ? beginIndex3
                    : 0;
            return substring.substring(beginIndex);
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

    public abstract void mergeHunter(Hunter hunter);

}
