package com.wang.eduservice.service.impl;

import com.wang.eduservice.entity.EduCourse;
import com.wang.eduservice.entity.EduCourseDescription;
import com.wang.eduservice.entity.vo.CourseInfoVo;
import com.wang.eduservice.entity.vo.CoursePublishVo;
import com.wang.eduservice.mapper.EduCourseMapper;
import com.wang.eduservice.service.EduChapterService;
import com.wang.eduservice.service.EduCourseDescriptionService;
import com.wang.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.eduservice.service.EduVideoService;
import com.wang.servicebase.exceptionhandler.TreemanException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-29
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述注入
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    //注入小节
    @Autowired
    private EduVideoService eduVideoService;

    //注入章节
    @Autowired
    private EduChapterService eduChapterService;


    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
       //1、向课程表添加课程基本信息
       // courseInfoVo对象转换到eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            //添加失败
            throw new TreemanException(20001,"添加课程信息失败");
        }
        //获取添加之后的课程id
        String cid = eduCourse.getId();
        //向课程简介表添加课程简介
        //edu_course_description
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);

        return cid;
    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1、查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2、查询描述表
        EduCourseDescription courseDescription =  courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1、修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new TreemanException(20001,"修改课程信息失败");
        }

        //2、修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    //根据课程id查询课程信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    //删除课程
    @Override
    public void removeCourse(String courseId){
        //根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);

        //根据课程id删除描述
        courseDescriptionService.removeById(courseId);

        //根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result == 0){
            throw new TreemanException(2001,"删除失败！");
        }

    }
}
