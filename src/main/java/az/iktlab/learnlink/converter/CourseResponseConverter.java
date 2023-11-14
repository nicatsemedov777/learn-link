package az.iktlab.learnlink.converter;

import az.iktlab.learnlink.entity.Course;
import az.iktlab.learnlink.model.response.course.CourseResponse;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class CourseResponseConverter implements Function<Course, CourseResponse> {
    @Override
    public CourseResponse apply(Course course) {
        return CourseResponse.builder()
                .name(course.getName())
                .price(course.getPrice())
                .createDate(course.getCreateDate().getTime())
                .subject(course.getSubject())

                .build();
    }
}
