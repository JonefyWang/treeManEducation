package com.wang.eduservice.mapper;

import com.wang.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-01-29
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据课程id查询课程确认信息
    public CoursePublishVo getPublishCourseInfo(String courseId);

}
