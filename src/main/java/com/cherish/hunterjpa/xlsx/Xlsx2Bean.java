package com.cherish.hunterjpa.xlsx;

import com.cherish.hunterjpa.domain.Hunter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 从Excel将简历导入mysql
 * Created by Administrator on 2017/2/18.
 */
public class Xlsx2Bean {

    public List<Hunter> excel2ResumesOfJson(String filePath){
        List<Hunter> hunters = new ArrayList<>();
        XSSFWorkbook ssfWorkbook = null;
        try {
            InputStream is = new FileInputStream(filePath);
            ssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            //// TODO: 2017/2/19
        }
        assert ssfWorkbook != null;
        XSSFSheet ssfSheet = ssfWorkbook.getSheetAt(0);
        for (int rowNum = 1; rowNum <= ssfSheet.getLastRowNum(); rowNum++) {
            XSSFRow ssfRow = ssfSheet.getRow(rowNum);
            hunters.add(getHunter(ssfRow));
        }
        return hunters;
    }

    private Hunter getHunter(XSSFRow ssfRow) {
        Hunter hunter;
        hunter = new Hunter();
        hunter.setName(getValue(ssfRow.getCell(0)));
        hunter.setPhone(getValue(ssfRow.getCell(1)));
        hunter.setAddress(getValue(ssfRow.getCell(2)));
        hunter.setStatus(getValue(ssfRow.getCell(3)));
        hunter.setOriginalPosition(getValue(ssfRow.getCell(4)));
        hunter.setUpdateTime(getValue(ssfRow.getCell(6)));
        hunter.setRemarks(getValue(ssfRow.getCell(7)));
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
            return String.valueOf(ssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(ssfCell.getStringCellValue());
        }
    }

    public static void main(String[] args) throws IOException {
        new Xlsx2Bean().excel2ResumesOfJson("E:\\hunter-springboot\\excel.xlsx");
    }

}
