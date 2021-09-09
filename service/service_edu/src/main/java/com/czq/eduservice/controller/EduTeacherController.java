package com.czq.eduservice.controller;


import com.czq.eduservice.entity.EduTeacher;
import com.czq.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //service注入
    @Autowired
    private EduTeacherService eduTeacherService;

    //1.查詢講師表中的所有數據
    //rest風格
    @GetMapping("findAll")
    public List<EduTeacher> finAllTeach() {

        List<EduTeacher> list = eduTeacherService.list(null);
        return list;
    }


}

