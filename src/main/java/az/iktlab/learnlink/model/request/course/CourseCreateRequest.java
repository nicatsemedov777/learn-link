package az.iktlab.learnlink.model.request.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCreateRequest {
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Subject is required")
    private String subject;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "10.00", message = "Course's price can not be less than 10$")
    @DecimalMax(value = "200.00" , message = "Course's price can not be more than 200$")
    private BigDecimal price;
}