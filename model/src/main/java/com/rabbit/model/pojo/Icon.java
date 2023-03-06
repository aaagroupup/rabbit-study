package com.rabbit.model.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_icon")
public class Icon implements Serializable {

    private String name;
    private String value;
    private String type;
}
