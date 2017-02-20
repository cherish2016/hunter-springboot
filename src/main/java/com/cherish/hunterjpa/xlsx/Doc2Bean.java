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
 * 解析doc文件入库mysql
 * Created by Administrator on 2017/2/19.
 */
public class Doc2Bean {

    public List<Hunter> getHuntersFromDoc(String filePath) {
        List<Hunter> hunters = new ArrayList<>();
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(filePath));
            WordExtractor extractor = new WordExtractor(in);
            String[] split = Optional.of(extractor.getText()).map(s -> s.split("\r\n")).orElse(new String[20]);
            Hunter hunter = new Hunter();
            hunter.setName(split[1].split("：")[1]);
            hunter.setPhone(split[2].split("：")[1]);
            hunter.setAddress(split[3]);
            hunter.setStatus(split[4]);
            hunter.setOriginalPosition(split[5]);
            hunter.setUpdateTime(split[8]);
            hunter.setRemarks(split[19]);
            hunter.setFileLink(filePath);
            hunters.add(hunter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                //
            }
        }
        return hunters;
    }

    public static void main(String[] args) {
        new Doc2Bean().getHuntersFromDoc("E:\\hunter-springboot\\test.doc");
    }
}
