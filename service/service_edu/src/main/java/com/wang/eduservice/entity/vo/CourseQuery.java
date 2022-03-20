package com.wang.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "发布状态 Normal已发布 Draft未发布")
    private String status;
}
