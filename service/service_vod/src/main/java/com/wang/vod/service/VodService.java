package com.wang.vod.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public abstract class VodService {
    //上传视频到阿里云
    public abstract String uploadVideoAly(MultipartFile file);
}
