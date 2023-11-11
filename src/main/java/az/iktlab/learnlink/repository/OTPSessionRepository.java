package az.iktlab.learnlink.repository;

import az.iktlab.learnlink.entity.OTPSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPSessionRepository extends JpaRepository<OTPSession, String> {
    Optional<OTPSession> findByOtpCodeAndUserIdAndIsUsedFalse(String otpCode, String userId);

}
