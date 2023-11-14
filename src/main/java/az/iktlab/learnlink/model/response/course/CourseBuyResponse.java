package az.iktlab.learnlink.model.response.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseBuyResponse {
    private BigDecimal balance;
    private String courseId;
    private String message;
}
