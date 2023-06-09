package com.rabbit.model.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectEeVo {

    @ExcelProperty(value="id",index=0)
    private Long id;
    @ExcelProperty(value="课程分类名称",index=1)
    private String title;
    @ExcelProperty(value="上级id",index=2)
    private Long pid;
    @ExcelProperty(value="排序",index=3)
    private Integer sort;
}
