package com.cherish.hunterjpa.controller;

import com.cherish.hunterjpa.domain.Hunter;
import com.cherish.hunterjpa.repository.HunterBaseRepository;
import com.cherish.hunterjpa.service.HunterSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Autowired
    private HunterBaseRepository baseRepository;

    @RequestMapping(value = "/search/{filed}/{value}",method = RequestMethod.GET)
    public List<Hunter> simpleSearchByOneField(@PathVariable("filed") String filed ,@PathVariable("value") String value) {
        switch (filed){
            case "name":
                return baseRepository.findByName(value);
            case "phone":
                return baseRepository.findByPhone(value);
            case "status":
                return baseRepository.findByStatus(value);
            case "remarks":
                return baseRepository.findByRemarksLike(value);
            case "originalPosition":
                return baseRepository.findByOriginalPositionLike(value);
        }
        return hunterSearchService.findByNameAndPhone("", "");
    }
}
