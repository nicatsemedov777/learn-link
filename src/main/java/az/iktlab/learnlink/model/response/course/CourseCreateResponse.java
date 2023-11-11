package az.iktlab.learnlink.model.response.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCreateResponse {
    private String id;
    private String name;
    private Long createDate;
    private String subject;
    private BigDecimal price;
}
