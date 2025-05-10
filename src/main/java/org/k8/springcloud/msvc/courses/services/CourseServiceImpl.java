package org.k8.springcloud.msvc.courses.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.k8.springcloud.msvc.courses.clients.UserClientRest;
import org.k8.springcloud.msvc.courses.models.UserDto;
import org.k8.springcloud.msvc.courses.models.entities.Course;
import org.k8.springcloud.msvc.courses.models.entities.CourseUser;
import org.k8.springcloud.msvc.courses.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;
    private UserClientRest userClientRest;

    public CourseServiceImpl(CourseRepository courseRepository, UserClientRest userClientRest) {
        this.courseRepository = courseRepository;
        this.userClientRest = userClientRest;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<UserDto> assignUser(UserDto user, Long courseId) {
        Optional<Course> o = courseRepository.findById(courseId);
        if(o.isPresent()){
            UserDto userDto = userClientRest.getUser(user.getId());

            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userDto.getId());
            course.addCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userDto);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserDto> createUser(UserDto user, Long courseId) {
        Optional<Course> o = courseRepository.findById(courseId);
        if(o.isPresent()){
            UserDto userDto = userClientRest.createUser(user);

            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userDto.getId());
            course.addCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userDto);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserDto> removeUser(UserDto user, Long courseId) {
        Optional<Course> o = courseRepository.findById(courseId);
        if(o.isPresent()){
            UserDto userDto = userClientRest.getUser(user.getId());

            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userDto.getId());
            course.removeCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userDto);
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findCourseByIdWithUsers(Long id) {
        Optional<Course> o = courseRepository.findById(id);
        if(o.isPresent()){
            Course course = o.get();
            if(!course.getCourseUsers().isEmpty()){
                List<Long> ids = course.getCourseUsers().stream()
                    .map(CourseUser::getUserId)
                    .collect(Collectors.toList());

                List<UserDto> client = userClientRest.getUsersByCourse(ids);
                course.setUsers(client);
            }
            return Optional.of(course);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteCourseUserById(Long userId) {
        courseRepository.deleteCourseUserById(userId);
    }
}
