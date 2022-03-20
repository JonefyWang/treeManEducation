package com.wang.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.eduservice.entity.EduSubject;
import com.wang.eduservice.entity.excel.SubjectData;
import com.wang.eduservice.entity.subject.OneSubject;
import com.wang.eduservice.entity.subject.TwoSubject;
import com.wang.eduservice.listener.SubjectExcelListener;
import com.wang.eduservice.mapper.EduSubjectMapper;
import com.wang.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-27
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try{
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //课程分类列表（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1、查出所有的一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);
        List<EduSubject> oneSubjectlist = baseMapper.selectList(wrapperOne);

        //2、查出所有的二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id",0);
        List<EduSubject> twoSubjectlist = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终的封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //3、封装一级分类
        //查询出来所有的一级分类集合遍历，得到每一个一级分类对象，获取每一个一级分类对象的值
        //封装到要求的List<OneSubject> finalSubjectList
        for (int i = 0; i < oneSubjectlist.size(); i++) {
            //得到oneSubjectlist每个EduSubject对象，并存到里面
            EduSubject eduSubject = oneSubjectlist.get(i);

            //把EduSubject中的值获取出来，放到oneSubject对象里面
            //多个oneSubject放到finalSubjectList里面
            OneSubject oneSubject =new OneSubject();
            //oneSubject.setId(eduSubject.getId());
            //oneSubject.setTitle(eduSubject.getTitle());
            //eduSubject复制到oneSubject中
            BeanUtils.copyProperties(eduSubject,oneSubject);

            finalSubjectList.add(oneSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每一个一级分类中的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            //遍历二级分类list集合
            for (int m = 0; m < twoSubjectlist.size(); m++) {
                //获取每一个二级分类
                EduSubject tSubject = twoSubjectlist.get(m);
                //判断二级分类中的parent_id与一级分类的id是否一样
                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    //把tSubject中的值复制到twoSubject里面，放到twofinalSubjectList中
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级分类下面的所有的二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }
        //4、封装二级分类
        return finalSubjectList;
    }
}
