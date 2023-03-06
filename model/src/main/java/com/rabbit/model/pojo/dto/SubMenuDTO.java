package com.rabbit.model.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rabbit.model.pojo.SubMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubMenuDTO extends SubMenu implements Serializable {
    @TableField(exist = false)
    private String fPath;

}
