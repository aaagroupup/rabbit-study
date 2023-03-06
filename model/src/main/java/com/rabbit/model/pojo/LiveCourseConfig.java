package com.rabbit.model.pojo;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author rabbit
 * @since 2023-02-08
 */
@TableName("live_course_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "LiveCourseConfig对象", description = "")
public class LiveCourseConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("live_course_id")
    private Long liveCourseId;

    private Integer pageViewMode;

    private Integer numberEnable;

    private Integer storeEnable;

    private Integer storeType;


}
