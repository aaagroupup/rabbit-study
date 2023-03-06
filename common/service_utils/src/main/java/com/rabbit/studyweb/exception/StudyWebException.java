package com.rabbit.studyweb.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyWebException extends RuntimeException{
    private Integer code;
    private String msg;
}
