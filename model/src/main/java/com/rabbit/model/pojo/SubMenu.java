package com.rabbit.model.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

//分支导航
@Data
@TableName("submenu")
public class SubMenu implements Serializable {

    private Integer id;
    private String title;
    private String path;

    private Integer pid;

    private String icon;

    private String pagePath;
}
