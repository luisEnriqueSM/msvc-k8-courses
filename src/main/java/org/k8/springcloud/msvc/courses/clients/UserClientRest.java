package org.k8.springcloud.msvc.courses.clients;

import org.k8.springcloud.msvc.courses.models.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-k8-users", url = "localhost:8001")
public interface UserClientRest {

    @GetMapping("/{id}")
    UserDto getUser(@PathVariable Long userId);

    @PostMapping
    UserDto createUser(@RequestBody UserDto user);

}
