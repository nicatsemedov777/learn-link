package az.iktlab.learnlink.model.otp;

import az.iktlab.learnlink.validator.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRecoverAccountRequest {
    @Email
    private String email;
}
