package com.wang.eduservice.service;

import com.wang.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.eduservice.entity.vo.CourseInfoVo;
import com.wang.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-29
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程基本信息的方法
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id, 查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);


    CoursePublishVo publishCourseInfo(String id);

    //删除课程
    void removeCourse(String courseId);
}
