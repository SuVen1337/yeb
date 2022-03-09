package com.ljn.server.pojo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ljn
 * @Date: 2022/02/26/12:20
 * @Description:分页返回模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResPageBean {
    //总条数
    private Long total;
    //数据
    private List<?> data;

}
