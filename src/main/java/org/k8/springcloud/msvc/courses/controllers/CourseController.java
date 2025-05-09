package org.k8.springcloud.msvc.courses.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.k8.springcloud.msvc.courses.entities.Course;
import org.k8.springcloud.msvc.courses.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Course>> list(){
        return ResponseEntity.ok().body(courseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id){
        return courseService.findCourseById(id).map(course -> ResponseEntity.ok().body(course))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Course course, BindingResult result){
        if(result.hasErrors()){
            return getErrors(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return getErrors(result);
        }
        Optional<Course> courseOptional = courseService.findCourseById(id);
        if(courseOptional.isPresent()){
            Course courseDB = courseOptional.get();
            courseDB.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return courseService.findCourseById(id).map(course -> {
            courseService.delete(id);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<?> getErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
