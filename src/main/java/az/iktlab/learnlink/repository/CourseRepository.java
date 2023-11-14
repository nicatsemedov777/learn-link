package az.iktlab.learnlink.repository;


import az.iktlab.learnlink.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {
    @Query("select c from Course as c where c.teacher.id = :teacherId")
    Optional<List<Course>> findAllByTeacherId(String teacherId);
}
