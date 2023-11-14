package az.iktlab.learnlink.repository;

import az.iktlab.learnlink.entity.Course;
import az.iktlab.learnlink.entity.CourseEnrollment;

import az.iktlab.learnlink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment,String> {

    @Query("SELECT ce.course FROM CourseEnrollment ce WHERE ce.student.id = :studentId")
    Optional<List<Course>> getAllCoursesByStudentId(String studentId);

    @Query("SELECT ce.student from CourseEnrollment  ce where ce.teacher.id =?1")
    Optional<List<User>> getAllUserByAuthorId(String teacherId);

}
