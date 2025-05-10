package org.k8.springcloud.msvc.courses.clients;

import java.util.List;

import org.k8.springcloud.msvc.courses.models.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msvc-k8-users", url = "localhost:8001")
public interface UserClientRest {

    @GetMapping("/{userId}")
    UserDto getUser(@PathVariable Long userId);

    @PostMapping
    UserDto createUser(@RequestBody UserDto user);

    @GetMapping("/usuarios-por-curso")
    List<UserDto> getUsersByCourse(@RequestParam Iterable<Long> usersIds);
}
