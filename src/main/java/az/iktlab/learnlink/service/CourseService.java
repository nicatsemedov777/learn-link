package az.iktlab.learnlink.service;

import az.iktlab.learnlink.model.request.course.CourseCreateRequest;
import az.iktlab.learnlink.model.request.course.CourseFilter;
import az.iktlab.learnlink.model.response.course.CourseBuyResponse;
import az.iktlab.learnlink.model.response.course.CourseCreateResponse;
import az.iktlab.learnlink.model.response.course.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface CourseService {
    CourseCreateResponse createCourse(CourseCreateRequest createRequest, Principal principal);
    Page<CourseResponse> getCoursePage(CourseFilter courseFilter, Pageable pageable, Principal principal);

    CourseBuyResponse buyCourse(String courseId, Principal principal);

    List<CourseResponse> getAllOwnCreatedCourses(Principal principal);
}
