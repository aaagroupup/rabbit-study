package com.rabbit.model.pojo.dto;

import com.rabbit.model.pojo.Course;
import lombok.Data;

import java.util.List;

@Data
public class SubjectDTO {

    private Long id;
    private String title;
    private List<Course>  courseList;
}
