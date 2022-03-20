package com.wang.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutils.R;
import com.wang.eduservice.entity.EduCourse;
import com.wang.eduservice.entity.EduTeacher;
import com.wang.eduservice.entity.vo.CourseInfoVo;
import com.wang.eduservice.entity.vo.CoursePublishVo;
import com.wang.eduservice.entity.vo.CourseQuery;
import com.wang.eduservice.entity.vo.TeacherQuery;
import com.wang.eduservice.service.EduCourseService;
import com.wang.servicebase.exceptionhandler.TreemanException;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //课程列表 基本实现
    //TODO 完善条件查询带分页(可以根据讲师来完善)
    @GetMapping
    public R getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    //分页查询课程的方法
    //current 当前页
    //limit 每页记录
    @GetMapping("pageCourse/{current}/{limit}")
    public R pageListCourse(@PathVariable long current, @PathVariable long limit) {

        //创建分页对象
        Page<EduCourse> pageCourse = new Page<>(current, limit);

        //测试异常
        try{
            int i = 10/0;
        }catch (Exception e){
            //执行自定义异常处理
            throw new TreemanException(20001,"执行了自定义异常处理...");
        }

        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTecher对象里面
        courseService.page(pageCourse, null);
        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> records = pageCourse.getRecords();//数据list 集合

//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("rows" ,records);
//        return R.ok().data(map);

        return R.ok().data("total", total).data("rows", records);

    }
    /**
     * 条件查询带分页的方法
     */
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) CourseQuery courseQuery) {
        //创建page对象
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //mybatis学过 动态sql
        String name = courseQuery.getName();
        String status = courseQuery.getStatus();
        //判断条件值是否为空，如果不为空则拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(status)) {
            //构建条件
            wrapper.eq("status", status);
        }

        //调用方法实现条件查询分页
        courseService.page(pageCourse, wrapper);
        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> records = pageCourse.getRecords();//数据list集合
        return R.ok().data("total", total).data("rows", records);
    }
    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加之后的课程id，为后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程id, 查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Nomal");//设置课程的发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

