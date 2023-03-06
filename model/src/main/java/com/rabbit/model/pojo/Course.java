package com.rabbit.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("sys_course")
@ApiModel(value = "Course对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("讲师id")
    private Long teacherId;

    @ApiModelProperty("课程id")
    private Long subjectId;

    @ApiModelProperty("课程父级id")
    private Long subjectPid;

    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("副标题")
    private String subTitle;

    @ApiModelProperty("课程价格")
    private BigDecimal price;

    @ApiModelProperty("视频封面")
    private String cover;

    @ApiModelProperty("销售数量")
    private Long buyCount;

    @ApiModelProperty("课程状态 0未发布，1已发布")
    private boolean status;

    @ApiModelProperty("课程发布时间")
    private String publishTime;

    @ApiModelProperty("课程简介")
    private String description;
}
