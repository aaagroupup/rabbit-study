package com.rabbit.studyweb.live.clients;

import com.rabbit.model.pojo.Teacher;
import com.rabbit.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("service-user")
public interface UserClient {
    @GetMapping("/teacher/inner/getTeacher/{id}")
    Teacher getTeacherInfo(@PathVariable Long id);

    @GetMapping("/user/inner/getUser")
    User getUserInfo();

    @GetMapping("/subject/inner/getSubjectName/{id}")
    String getSubjectName(@PathVariable Long id);
}
