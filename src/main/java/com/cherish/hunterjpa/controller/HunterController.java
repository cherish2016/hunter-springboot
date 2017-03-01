package com.cherish.hunterjpa.controller;

import com.cherish.hunterjpa.domain.Hunter;
import com.cherish.hunterjpa.service.HunterSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 对外接口类
 * Created by cherish on 2017/2/18.
 */
@RestController
public class HunterController {

    protected static Logger logger = LoggerFactory.getLogger(HunterController.class);

    @Autowired
    private HunterSearchService hunterSearchService;

    @GetMapping("/search")
    public List<Hunter> h1() {
        return hunterSearchService.findByNameAndPhone("高明", "15910666477");
    }
}
