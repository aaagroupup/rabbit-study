package com.rabbit.model.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class HomeMenusDTO {

    private Long id;

    private String title;

    private List<CourseDTO> courseList;
}
