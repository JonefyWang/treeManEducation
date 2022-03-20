package com.wang.eduservice.controller;

import com.wang.commonutils.R;
import com.wang.eduservice.entity.subject.OneSubject;
import com.wang.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-27
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来的的文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubbject (MultipartFile file){
        //上传过来的文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    //课程分类的列表显示
    @GetMapping("getAllSubject")
    public R getAllSubject(){
         List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

