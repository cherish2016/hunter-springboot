package com.cherish.hunterjpa.repository;

import com.cherish.hunterjpa.domain.Hunter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 简历信息查询接口
 * Created by cherish on 2017/2/18.
 */
public interface HunterBaseRepository extends JpaRepository<Hunter,String>{
}
