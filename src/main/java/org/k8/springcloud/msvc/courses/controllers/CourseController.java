package org.k8.springcloud.msvc.courses.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.k8.springcloud.msvc.courses.models.UserDto;
import org.k8.springcloud.msvc.courses.models.entities.Course;
import org.k8.springcloud.msvc.courses.services.CourseService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
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

import feign.FeignException;
import jakarta.validation.Valid;

@RestController
public class CourseController {

    private CourseService courseService;
    private ApplicationContext context;
    private Environment env;

    public CourseController(CourseService courseService, Environment environment) {
        this.courseService = courseService;
        this.env = environment;
    }

    @GetMapping("/crash")
    public void crash(){
        ((ConfigurableApplicationContext)context).close();
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> list(){
        Map<String, Object> body = new HashMap<>();
        body.put("courses", courseService.findAll());
        body.put("pod_Info", env.getProperty("MY_POD_NAME") + " : " + env.getProperty("MY_POD_IP"));
        body.put("texto", env.getProperty("config.texto"));
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id){
        return courseService.findCourseByIdWithUsers(id).map(course -> ResponseEntity.ok().body(course))
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

    @PutMapping("/asignar-usuario/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody UserDto userDto, @PathVariable Long courseId){
        Optional<UserDto> optionalUserDto;
        try {
            optionalUserDto = courseService.assignUser(userDto, courseId);
        } catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("mensaje", "No existe el usuario por id o error en la comunicación: " + e.getMessage()));
        }

        if(optionalUserDto.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUserDto.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto, @PathVariable Long courseId){
        Optional<UserDto> optionalUserDto;
        try {
            optionalUserDto = courseService.createUser(userDto, courseId);
        } catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("mensaje", "No se pudo crear el usuario por id o error en la comunicación: " + e.getMessage()));
        }

        if(optionalUserDto.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUserDto.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{courseId}")
    public ResponseEntity<?> removeUser(@RequestBody UserDto userDto, @PathVariable Long courseId){
        Optional<UserDto> optionalUserDto;
        try {
            optionalUserDto = courseService.removeUser(userDto, courseId);
        } catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("mensaje", "No existe el usuario por id o error en la comunicación: " + e.getMessage()));
        }

        if(optionalUserDto.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(optionalUserDto.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-curso-usuario/{userId}")
    public ResponseEntity<?> deleteCourseUser(@PathVariable Long userId){
        courseService.deleteCourseUserById(userId);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<?> getErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
