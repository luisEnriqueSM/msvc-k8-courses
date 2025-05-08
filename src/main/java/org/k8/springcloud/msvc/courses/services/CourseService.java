package org.k8.springcloud.msvc.courses.services;

import java.util.Optional;

import org.k8.springcloud.msvc.courses.entities.Course;

public interface CourseService {

    Iterable<Course> findAll();
    Optional<Course> findCourseById(Long id);
    Course save(Course course);
    void delete(Long id);

}
