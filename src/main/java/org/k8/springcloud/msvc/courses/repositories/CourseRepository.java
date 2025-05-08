package org.k8.springcloud.msvc.courses.repositories;

import org.k8.springcloud.msvc.courses.entities.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long>{

}
