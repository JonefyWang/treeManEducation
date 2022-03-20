package com.wang.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.eduservice.entity.EduChapter;
import com.wang.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-29
 */
public interface EduVideoService extends IService<EduVideo> {

    void list(QueryWrapper<EduChapter> wrapperVideo);

    //根据课程id删除小节
    void removeVideoByCourseId(String courseId);
}
