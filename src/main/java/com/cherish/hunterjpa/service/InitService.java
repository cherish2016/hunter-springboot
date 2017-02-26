package com.cherish.hunterjpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 应用启动默认操作
 * Created by Administrator on 2017/2/19.
 */
@Component
public class InitService {

    @Autowired
    private HunterOperateService hunterOperateService;

    @PostConstruct
    public void init() {
        hunterOperateService.saveXlsx2Mysql("E:\\hunterdao\\home");
        hunterOperateService.saveDoc2Mysql("E:\\hunterdao\\home");
    }

}
