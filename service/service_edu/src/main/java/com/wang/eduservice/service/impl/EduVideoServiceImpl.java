package com.wang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.eduservice.entity.EduChapter;
import com.wang.eduservice.entity.EduVideo;
import com.wang.eduservice.mapper.EduVideoMapper;
import com.wang.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-29
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    //1、课程列表
    @Override
    public void list(QueryWrapper<EduChapter> wrapperVideo) {

    }

    //2、根据课程id删除小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
