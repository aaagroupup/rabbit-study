package com.rabbit.model.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryInfo implements Serializable {

    private String query;//查询信息 username
    private int currentPage;//当前页码
    private int pageSize;//页数
}
