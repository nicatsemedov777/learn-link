package az.iktlab.learnlink.model.request;


import az.iktlab.learnlink.enums.Profession;
import az.iktlab.learnlink.validator.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignUpRequest {
    @NotEmpty(message = "Username is required")
    @Size(max = 40, message = "Username cannot be longer than 40 characters")
    private String username;
    @Email
    private String email;
    @NotEmpty(message = "Password is required")
    @Size(max = 20, message = "Password cannot be longer than 20 characters")
    private String password;
    @NotNull(message = "Born date is required")
    private Long bornDate;
    @NotNull(message = "Profession is required")
    private Profession profession;
}
