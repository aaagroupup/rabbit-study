package com.rabbit.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MainMenu implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String title;
    private String path;
    private String icon;
    private String pagePath;
    private List<SubMenu> subMenuList;

}
