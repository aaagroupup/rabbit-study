package com.rabbit.model.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseDTO {

    private Long id;

    private String title;

    private String subTitle;

    private String cover;

    private Long buyCount;

    private String description;

    private BigDecimal price;
}
