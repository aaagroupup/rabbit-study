package com.rabbit.model.pojo.dto;

import com.rabbit.model.pojo.LiveCourse;
import com.rabbit.model.pojo.Teacher;
import lombok.Data;

@Data
public class LiveCourseDTO extends LiveCourse {
    private Teacher teacher;

    private Integer liveStatus;

    private String startTimeString;
    private String endTimeString;
}
