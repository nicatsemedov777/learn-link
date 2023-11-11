package az.iktlab.learnlink.controller;

import az.iktlab.learnlink.model.request.course.CourseCreateRequest;
import az.iktlab.learnlink.model.request.course.CourseFilter;
import az.iktlab.learnlink.model.response.course.CourseCreateResponse;
import az.iktlab.learnlink.model.response.course.CourseResponse;
import az.iktlab.learnlink.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static az.iktlab.learnlink.util.ResponseBuilder.buildResponse;

@RestController
@RequestMapping("api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<CourseCreateResponse> createCourse(@RequestBody @Valid CourseCreateRequest createRequest, Principal principal) {
        return buildResponse(courseService.createCourse(createRequest,principal));
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> getCoursePage(@ParameterObject CourseFilter courseFilter,
                                                              @ParameterObject Pageable pageable,
                                                              Principal principal) {
        return buildResponse(courseService.getCoursePage(courseFilter, pageable, principal));
    }
}
