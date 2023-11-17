package az.iktlab.learnlink.service.impl;


import az.iktlab.learnlink.configuration.CustomEventPublisher;
import az.iktlab.learnlink.converter.CourseResponseConverter;
import az.iktlab.learnlink.entity.Course;
import az.iktlab.learnlink.entity.OTPSession;
import az.iktlab.learnlink.entity.Role;
import az.iktlab.learnlink.entity.User;
import az.iktlab.learnlink.error.exception.AuthenticationException;
import az.iktlab.learnlink.error.exception.OTPSessionExpiredException;
import az.iktlab.learnlink.error.exception.ResourceAlreadyExistsException;
import az.iktlab.learnlink.error.exception.ResourceNotFoundException;
import az.iktlab.learnlink.event.OTPEvent;
import az.iktlab.learnlink.model.jwt.JwtToken;
import az.iktlab.learnlink.model.otp.UserRecoverAccountOTPRequest;
import az.iktlab.learnlink.model.otp.UserRecoverAccountRequest;
import az.iktlab.learnlink.model.request.UserSignInRequest;
import az.iktlab.learnlink.model.request.UserSignUpRequest;
import az.iktlab.learnlink.model.response.course.CourseResponse;
import az.iktlab.learnlink.repository.CourseEnrollmentRepository;
import az.iktlab.learnlink.repository.OTPSessionRepository;
import az.iktlab.learnlink.repository.RoleRepository;
import az.iktlab.learnlink.repository.UserRepository;
import az.iktlab.learnlink.security.JWTProvider;
import az.iktlab.learnlink.service.UserService;
import az.iktlab.learnlink.util.DateHelper;
import az.iktlab.learnlink.validator.OTPGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;
    private final OTPSessionRepository otpSessionRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseResponseConverter courseResponseConverter;
    private final CustomEventPublisher eventPublisher;


    @Override
    public JwtToken signUp(UserSignUpRequest userSignUpRequest) {
        boolean existsByEmail = userRepository.existsByEmailAndIsDeletedFalse(userSignUpRequest.getEmail());

        if (existsByEmail) {
            throw new ResourceAlreadyExistsException("User already exists with this email: " + userSignUpRequest.getEmail());
        }

        User newUser = buildNewUser(userSignUpRequest);
        userRepository.save(newUser);

        return jwtProvider.getJWTToken(newUser.getId());
    }

    @Override
    public JwtToken signIn(UserSignInRequest userSignInRequest) {
        User user = userRepository.findByEmailAndIsDeletedFalse(userSignInRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this email:"
                        + userSignInRequest.getEmail()));

        if (passwordEncoder.matches(userSignInRequest.getPassword(), user.getPassword())) {
            return jwtProvider.getJWTToken(user.getId());
        }

        throw new AuthenticationException("Bad credentials");
    }

    @Override
    public void sendOTPForRecover(UserRecoverAccountRequest userRecoverAccountRequest) {
        User user = userRepository.findByEmailAndIsDeletedFalse(userRecoverAccountRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this email: "
                        + userRecoverAccountRequest.getEmail()));

        OTPSession otpSession = createOTPSession(user);
        otpSessionRepository.save(otpSession);
        eventPublisher.publishEvent(buildOTPEvent(user, otpSession));
    }

    private static OTPEvent buildOTPEvent(User user, OTPSession otpSession) {
        return OTPEvent.builder()
                .email(user.getEmail())
                .otpCode(otpSession.getOtpCode())
                .build();
    }

    @Override
    public void recoverAccount(UserRecoverAccountOTPRequest userRecoverAccountOTPRequest) {
        User user = userRepository.findByEmailAndIsDeletedFalse(userRecoverAccountOTPRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        OTPSession otpSession = otpSessionRepository.findByOtpCodeAndUserIdAndIsUsedFalse(
                        userRecoverAccountOTPRequest.getOtp(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Otp session not found."));

        checkOtpSessionIsExpired(otpSession.getCreateDate());

        user.setPassword(passwordEncoder.encode(userRecoverAccountOTPRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void increaseBalance(BigDecimal amount, Principal principal) {
        var user = userRepository.findById(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);
    }

    @Override
    public BigDecimal getBalance(Principal principal) {
        return userRepository.findBalanceById(principal.getName()).getBalance();
    }

    @Override
    public List<CourseResponse> getAllCourses(Principal principal) {
        List<Course> courses = courseEnrollmentRepository.getAllCoursesByStudentId(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException("Course not found in this student"));


        return courses.stream()
                .map(courseResponseConverter)
                .collect(Collectors.toList());
    }


    private void checkOtpSessionIsExpired(Date createDate) {
        Calendar now = Calendar.getInstance();
        Calendar otpCreateDate = Calendar.getInstance();
        otpCreateDate.setTime(createDate);
        otpCreateDate.add(Calendar.MINUTE, 3);
        if (now.after(otpCreateDate)) {
            throw new OTPSessionExpiredException("Otp session is expired");
        }
    }

    private static OTPSession createOTPSession(User user) {
        return OTPSession.builder()
                .userId(user.getId())
                .isUsed(false)
                .createDate(DateHelper.now())
                .otpCode(OTPGenerator.generate())
                .build();
    }

    private User buildNewUser(UserSignUpRequest userSignUpRequest) {
        Role role = roleRepository.findByName(userSignUpRequest.getProfession().name());

        return User.builder()
                .roles(Set.of(role))
                .email(userSignUpRequest.getEmail())
                .username(userSignUpRequest.getUsername())
                .bornDate(DateHelper.now(userSignUpRequest.getBornDate()))
                .createDate(DateHelper.now())
                .balance(BigDecimal.ZERO)
                .password(passwordEncoder.encode(userSignUpRequest.getPassword()))
                .isDeleted(false)
                .build();
    }
}
