package com.rabbit.model.pojo;
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
@TableName("live_course_description")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "LiveCourseDescription对象", description = "")
public class LiveCourseDescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long liveCourseId;

    private String description;


}
