package com.rabbit.model.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author rabbit
 * @since 2023-02-09
 */
@TableName("video_visitor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "VideoVisitor对象", description = "")
public class VideoVisitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long courseId;

    private Integer userId;

    private String nickName;

    private LocalDateTime joinTime;

    private LocalDateTime leaveTime;

    private Integer duration;


}
