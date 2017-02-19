package com.cherish.hunterjpa.xlsx;

import com.cherish.hunterjpa.domain.Hunter;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * 解析doc文件入库mysql
 * Created by Administrator on 2017/2/19.
 */
public class Doc2Bean {

    public List<Hunter> getHuntersFromDoc(String filePath) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(filePath));
            WordExtractor extractor = new WordExtractor(in);
            String str = extractor.getText();
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
        return null;
    }

    public static void main(String[] args) {
        new Doc2Bean().getHuntersFromDoc("E:\\hunter-springboot\\test.doc");
    }
}
