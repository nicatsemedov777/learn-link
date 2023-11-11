package az.iktlab.learnlink.entity;


import az.iktlab.learnlink.entity.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "otp_sessions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OTPSession {

    @Id
    @GeneratedValue(generator = "id-generator")
    @GenericGenerator(name = "id-generator", type = IdGenerator.class)
    private String id;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "is_used")
    private Boolean isUsed;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "otp_code")
    private String otpCode;

}
