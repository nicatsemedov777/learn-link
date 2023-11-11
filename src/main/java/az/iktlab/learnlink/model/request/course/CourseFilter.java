package az.iktlab.learnlink.model.request.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseFilter {
    private String searchText;
    private Long minDate;
    private Long maxDate;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    @JsonIgnore
    private String userId;
}
