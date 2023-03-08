package com.rabbit.model.pojo.dto;

import com.rabbit.model.pojo.Advertise;
import com.rabbit.model.pojo.Course;
import lombok.Data;

import java.util.List;

@Data
public class HomeMenusBannerDTO {
    
    private List<HomeMenusDTO> homeMenusList;

    private List<Course> advertiseList;
}
