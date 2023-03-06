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
@TableName("live_course_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "LiveCourseAccount对象", description = "")
public class LiveCourseAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long liveCourseId;

    private String zhuboAccount;

    private String zhuboPassword;

    private String zhuboKey;

    private String adminKey;

    private String userKey;


}
