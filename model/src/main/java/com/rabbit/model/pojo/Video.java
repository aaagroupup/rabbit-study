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
 * @since 2023-02-07
 */
@TableName("sys_video")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Video对象", description = "")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("课程id")
    private Long courseId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("云端视频资源")
    private String videoSourceId;

    @ApiModelProperty("视频原始文件名称")
    private String videoOriginalName;


}
