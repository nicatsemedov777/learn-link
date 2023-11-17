package az.iktlab.learnlink.service.impl;

import az.iktlab.learnlink.configuration.CustomEventPublisher;
import az.iktlab.learnlink.converter.CourseResponseConverter;
import az.iktlab.learnlink.entity.Course;
import az.iktlab.learnlink.entity.CourseEnrollment;
import az.iktlab.learnlink.entity.User;
import az.iktlab.learnlink.error.exception.ResourceNotFoundException;
import az.iktlab.learnlink.event.NewCourseNotificationEvent;
import az.iktlab.learnlink.model.request.course.CourseCreateRequest;
import az.iktlab.learnlink.model.request.course.CourseFilter;
import az.iktlab.learnlink.model.response.course.CourseBuyResponse;
import az.iktlab.learnlink.model.response.course.CourseCreateResponse;
import az.iktlab.learnlink.model.response.course.CourseResponse;
import az.iktlab.learnlink.repository.CourseEnrollmentRepository;
import az.iktlab.learnlink.repository.CourseRepository;
import az.iktlab.learnlink.repository.UserRepository;
import az.iktlab.learnlink.service.CourseService;
import az.iktlab.learnlink.specification.CourseSpecification;
import az.iktlab.learnlink.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseResponseConverter courseResponseConverter;
    private final CourseEnrollmentRepository courseEnrollmentRepository;

    private final CustomEventPublisher eventPublisher;

    @Override
    public CourseCreateResponse createCourse(CourseCreateRequest createRequest, Principal principal) {
        User user = userRepository.findById(principal.getName()).orElseThrow(ResourceNotFoundException::new);
        Course course = buildCourse(createRequest, user);
        courseRepository.save(course);

        List<User> users = courseEnrollmentRepository.getAllUserByAuthorId(principal.getName())
                .orElseThrow(ResourceNotFoundException::new);

        if (CollectionUtils.isNotEmpty(users)) {
            String[] to = getEmailsFromListOfUsers(users);
            eventPublisher.publishEvent(buildNewCourseNotificationEvent(course, to));
        }
        return getCourseCreateResponse(course);
    }

    private static NewCourseNotificationEvent buildNewCourseNotificationEvent(Course course, String[] to) {
        return NewCourseNotificationEvent.builder()
                .courseName(course.getName())
                .authorName(course.getTeacher().getUsername())
                .emails(to)
                .build();
    }

    private static String[] getEmailsFromListOfUsers(List<User> users) {
        String[] to = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            to[i] = users.get(i).getEmail();
        }
        return to;
    }

    @Override
    public Page<CourseResponse> getCoursePage(CourseFilter courseFilter, Pageable pageable, Principal principal) {
        User user = userRepository.findById(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + principal.getName()));
        courseFilter.setUserId(user.getId());

        CourseSpecification courseSpecification = new CourseSpecification(courseFilter);
        List<CourseResponse> responseList = courseRepository.findAll(courseSpecification, pageable)
                .map(courseResponseConverter)
                .toList();

        return new PageImpl<>(responseList, pageable, pageable.getPageSize());
    }

    @Override
    public CourseBuyResponse buyCourse(String courseId, Principal principal) {
        var course = courseRepository.findById(courseId).orElseThrow(() ->
                new ResourceNotFoundException("There is no course with this courseId."));
        var student = userRepository.findById(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException("There is no user with this userId."));

        if (checkBalance(course, student))
            return getBuyResponse(courseId, student, "You do not have enough balance to purchase the course");

        CourseEnrollment courseEnrollment = builtCourseEnrollment(course, student);
        courseEnrollmentRepository.save(courseEnrollment);
        userRepository.save(student);
        return getBuyResponse(courseId, student, "You have successfully purchase the course");
    }

    @Override
    public List<CourseResponse> getAllOwnCreatedCourses(Principal principal) {
        List<Course> courses = courseRepository.findAllByTeacherId(principal.getName())
                .orElseThrow(ResourceNotFoundException::new);
        return courses
                .stream()
                .map(courseResponseConverter)
                .collect(Collectors.toList());
    }


    private static CourseBuyResponse getBuyResponse(String courseId, User student, String message) {
        return CourseBuyResponse.builder()
                .courseId(courseId)
                .balance(student.getBalance())
                .message(message)
                .build();
    }

    private static boolean checkBalance(Course course, User student) {
        int balanceIsEnough = student.getBalance().compareTo(course.getPrice());

        if (balanceIsEnough < 0) {
            return true;
        }
        student.setBalance(student.getBalance().subtract(course.getPrice()));
        return false;
    }

    private static CourseEnrollment builtCourseEnrollment(Course course, User student) {
        return CourseEnrollment.builder()
                .teacher(course.getTeacher())
                .student(student)
                .course(course)
                .build();
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
