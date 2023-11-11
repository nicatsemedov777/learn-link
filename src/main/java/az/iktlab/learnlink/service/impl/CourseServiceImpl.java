package az.iktlab.learnlink.service.impl;

import az.iktlab.learnlink.converter.CourseResponseConverter;
import az.iktlab.learnlink.entity.Course;
import az.iktlab.learnlink.entity.User;
import az.iktlab.learnlink.error.exception.ResourceNotFoundException;
import az.iktlab.learnlink.model.request.course.CourseCreateRequest;
import az.iktlab.learnlink.model.request.course.CourseFilter;
import az.iktlab.learnlink.model.response.course.CourseCreateResponse;
import az.iktlab.learnlink.model.response.course.CourseResponse;
import az.iktlab.learnlink.repository.CourseRepository;
import az.iktlab.learnlink.repository.UserRepository;
import az.iktlab.learnlink.service.CourseService;
import az.iktlab.learnlink.specification.CourseSpecification;
import az.iktlab.learnlink.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseResponseConverter courseResponseConverter;

    @Override
    public CourseCreateResponse createCourse(CourseCreateRequest createRequest, Principal principal) {
        User user= userRepository.findById(principal.getName()).orElseThrow(ResourceNotFoundException::new);
        Course course = buildCourse(createRequest, user);
        courseRepository.save(course);
        return getCourseCreateResponse(course);
    }

    @Override
    public Page<CourseResponse> getCoursePage(CourseFilter courseFilter, Pageable pageable, Principal principal) {
        User user = userRepository.findById(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + principal.getName()));
        courseFilter.setUserId(user.getId());

        CourseSpecification courseSpecification = new CourseSpecification(courseFilter);
        List<CourseResponse> responseList =  courseRepository.findAll(courseSpecification, pageable)
                .map(courseResponseConverter)
                .toList();

        return new PageImpl<>(responseList, pageable, pageable.getPageSize());
    }
    private static CourseCreateResponse getCourseCreateResponse(Course course) {
        return CourseCreateResponse.builder()
                .id(course.getId())
                .createDate(course.getCreateDate().getTime())
                .subject(course.getSubject())
                .name(course.getName())
                .price(course.getPrice())
                .build();
    }

    private static Course buildCourse(CourseCreateRequest createRequest, User user) {
        return Course.builder()
                .name(createRequest.getName())
                .subject(createRequest.getSubject())
                .price(createRequest.getPrice())
                .createDate(DateHelper.now())
                .teacher(user)
                .build();
    }
}
