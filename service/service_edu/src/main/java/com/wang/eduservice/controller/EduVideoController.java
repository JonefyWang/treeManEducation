package com.wang.eduservice.controller;


import com.wang.commonutils.R;
import com.wang.eduservice.client.VodClient;
import com.wang.eduservice.entity.EduVideo;
import com.wang.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    //注入vodClient
    @Autowired
    private VodClient vodClient;

    //根据小节id查询
    @GetMapping("getVideoInfo/{videoId}")
    public R getChapterInfo(@PathVariable String videoId){
        EduVideo eduVideo= videoService.getById(videoId);
        return R.ok().data("video",eduVideo);
    }

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id获取视频id，调用方法实现视频删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小姐里面是否有视频id
        if (!StringUtils.isEmpty(videoSourceId)) {
            //根据视频id，远程调用实现删除视频
            vodClient.removeAlyVideo(videoSourceId);
        }
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }

    //修改章节
    @PostMapping("updateVideo")
    public R updateChapter(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo );
        return R.ok();
    }

}

