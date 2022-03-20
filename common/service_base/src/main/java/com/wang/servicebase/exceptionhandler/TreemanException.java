package com.wang.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor//生成有参构造
@NoArgsConstructor//生成无参构造
public class TreemanException extends RuntimeException{

    private Integer code;//状态码
    private String msg;//异常信息

}
