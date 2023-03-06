package com.rabbit.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2023-02-05
 */
@TableName("sys_subject")
@ApiModel(value = "Subject对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("类别名称")
    private String title;

    @ApiModelProperty("父ID")
    private Long pid;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("更新时间")
    private String updateTime;

    @TableField(exist = false)
    private boolean hasChildren;



}
