package az.iktlab.learnlink.service;

import az.iktlab.learnlink.model.jwt.JwtToken;
import az.iktlab.learnlink.model.otp.UserRecoverAccountOTPRequest;
import az.iktlab.learnlink.model.otp.UserRecoverAccountRequest;
import az.iktlab.learnlink.model.request.UserSignInRequest;
import az.iktlab.learnlink.model.request.UserSignUpRequest;
import az.iktlab.learnlink.model.response.course.CourseResponse;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface UserService {
    JwtToken signUp(UserSignUpRequest userSignUpRequest);
    JwtToken signIn(UserSignInRequest userSignInRequest);
    void sendOTPForRecover(UserRecoverAccountRequest userRecoverAccountRequest);
    void recoverAccount(UserRecoverAccountOTPRequest userRecoverAccountOTPRequest);

    void increaseBalance(BigDecimal amount, Principal principal);

    BigDecimal getBalance(Principal principal);

    List<CourseResponse> getAllCourses(Principal principal);
}
