package com.rabbit.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author rabbit
 * @since 2023-02-06
 */
@TableName("sys_teacher")
@ApiModel(value = "Teacher对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("讲师名字")
    private String name;

    @ApiModelProperty("讲师介绍")
    private String description;

    @ApiModelProperty("教师照片")
    private String img;

    @ApiModelProperty("讲师等级")
    private String level;

}
