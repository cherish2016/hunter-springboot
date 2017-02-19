package com.cherish.hunterjpa.service;

import com.cherish.hunterjpa.domain.Hunter;
import com.cherish.hunterjpa.repository.HunterBaseRepository;
import com.cherish.hunterjpa.xlsx.Xlsx2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<Hunter> s1 () {
        return baseRepository.findAll();
    }

}
