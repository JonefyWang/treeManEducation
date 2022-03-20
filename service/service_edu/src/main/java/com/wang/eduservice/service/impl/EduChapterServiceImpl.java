package com.wang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.eduservice.entity.EduChapter;
import com.wang.eduservice.entity.EduVideo;
import com.wang.eduservice.entity.chapter.ChapterVo;
import com.wang.eduservice.entity.chapter.VideoVo;
import com.wang.eduservice.mapper.EduChapterMapper;
import com.wang.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.eduservice.service.EduVideoService;
import com.wang.servicebase.exceptionhandler.TreemanException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-29
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;//注入小节service

    //课程大纲列表，根据课程id进行查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //1、根据课程id查询里面的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2、根据课程id查询里面的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建list集合用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3、遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //eduChapter的对象复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把ChapterVo放到List集合中
            finalList.add(chapterVo);

            //创建集合，用于封装章节的小节
            List<VideoVo> videoList = new ArrayList<>();

            //遍历查询list小节部分
            for (int m = 0; m < eduVideoList.size(); m++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                //判断小节里面的chapterid与章节里面的id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    //放到小节封装集合中
                    videoList.add(videoVo);
                }
            }

            //把封装到小节list集合放到章节对象里面
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

    //删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterId章节id查询 小节表，如果查询到数据，不进行删除
        QueryWrapper<EduVideo> wrapper =  new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断
        if (count > 0){
            //查询出小节，不进行删除
            throw new TreemanException(20001,"查询出小节，不能删除");
        }else{
            //不能查询出数据，进行删除
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
    QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
    wrapper.eq("course_id",courseId);
    baseMapper.delete(wrapper);
    }
}
