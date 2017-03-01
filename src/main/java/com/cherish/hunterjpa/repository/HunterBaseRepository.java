package com.cherish.hunterjpa.repository;

import com.cherish.hunterjpa.domain.Hunter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 简历信息查询接口
 * Created by cherish on 2017/2/18.
 */
public interface HunterBaseRepository extends JpaRepository<Hunter, String> {

    List<Hunter> findByName(String name);

    List<Hunter> findByPhone(String phone);

    List<Hunter> findByNameAndPhone(String name, String phone);

    List<Hunter> findByOriginalPositionLike(String originalPosition);

    List<Hunter> findByRemarksLike(String remarks);

    List<Hunter> findByStatus(String status);

    List<Hunter> findByEducation(String education);

    List<Hunter> findByworkingYears(String workingYears);

}

