package com.rabbit.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("其他参数")
    @TableField(exist = false)
    private Map<String,Object> param=new HashMap<>();

}
