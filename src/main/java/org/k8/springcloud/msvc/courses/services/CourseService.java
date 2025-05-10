package org.k8.springcloud.msvc.courses.services;

import java.util.Optional;

import org.k8.springcloud.msvc.courses.models.UserDto;
import org.k8.springcloud.msvc.courses.models.entities.Course;

public interface CourseService {

    Iterable<Course> findAll();
    Optional<Course> findCourseById(Long id);
    Course save(Course course);
    void delete(Long id);

    Optional<UserDto> assignUser(UserDto user, Long courseId);
    Optional<UserDto> createUser(UserDto user, Long courseId);
    Optional<UserDto> removeUser(UserDto user, Long courseId);
}
