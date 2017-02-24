package com.cherish.hunterjpa.xlsx;

import com.cherish.hunterjpa.domain.Hunter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 从Excel将简历导入mysql
 * Created by Administrator on 2017/2/18.
 */
public abstract class Xlsx2Bean {

    private static List<File> filesOfDir = new ArrayList<>();

    public void excel2Resumes(String filePath) {
        initFilePathOfDir(new File(filePath));
        filesOfDir.forEach(file -> {
            List<Hunter> hunters = excel2ResumesOfJson(file.getPath());
            insertHunter2Mysql(hunters);
        });
    }

    public abstract void insertHunter2Mysql(List<Hunter> hunters);

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
                if (f.getName().endsWith(".xlsx")) {
                    filesOfDir.add(f);
                }
            }
        }
    }

    public List<Hunter> excel2ResumesOfJson(String filePath) {
        List<Hunter> hunters = new ArrayList<>();
        XSSFWorkbook ssfWorkbook;
        try {
            InputStream is = new FileInputStream(filePath);
            ssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet ssfSheet = ssfWorkbook.getSheetAt(0);
            for (int rowNum = 1; rowNum <= ssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow ssfRow = ssfSheet.getRow(rowNum);
                hunters.add(getHunter(ssfRow));
            }
        } catch (IOException e) {
            return hunters;
            //// TODO: 2017/2/19
        }
        return hunters;
    }

    private Hunter getHunter(XSSFRow ssfRow) {
        Hunter hunter;
        hunter = new Hunter();
        try {
            hunter.setName(getValue(ssfRow.getCell(0)));
            hunter.setPhone(getValue(ssfRow.getCell(1)));
            hunter.setAddress(getValue(ssfRow.getCell(2)));
            hunter.setStatus(getValue(ssfRow.getCell(3)));
            hunter.setOriginalPosition(getValue(ssfRow.getCell(4)));
            if (getValue(ssfRow.getCell(5)).equals("整理")){
                hunter.setUpdateTime(getValue(ssfRow.getCell(6)));
                hunter.setRemarks(getValue(ssfRow.getCell(7)));
            } else {
                hunter.setUpdateTime(getValue(ssfRow.getCell(5)));
                hunter.setRemarks(getValue(ssfRow.getCell(6)));
            }
        } catch (Exception e) {
            hunter.setPhone("0");
            return hunter;
        }
        return hunter;
    }

    /**
     * 得到Excel表中的值
     *
     * @param ssfCell Excel中的每一个格子
     * @return Excel中每一个格子中的值
     */
    @SuppressWarnings("static-access")
    private String getValue(XSSFCell ssfCell) {
        if (ssfCell == null)
            return "";
        if (ssfCell.getCellType() == ssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(ssfCell.getBooleanCellValue());
        } else if (ssfCell.getCellType() == ssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return new DecimalFormat("#").format(ssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(ssfCell.getStringCellValue());
        }
    }

}
