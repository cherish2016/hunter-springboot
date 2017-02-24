package com.cherish.hunterjpa.service;

import com.cherish.hunterjpa.domain.Hunter;
import com.cherish.hunterjpa.repository.HunterBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 查询简历信息处理类
 * Created by cherish on 2017/2/18.
 */
@Component
public class HunterSearchService {

    @Autowired
    private HunterBaseRepository baseRepository;

    public List<Hunter> findByNameAndPhone(String name, String phone) {
        return baseRepository.findByNameAndPhone(name, phone);
    }


}
