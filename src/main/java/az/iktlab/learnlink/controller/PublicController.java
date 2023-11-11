package az.iktlab.learnlink.controller;


import az.iktlab.learnlink.model.otp.UserRecoverAccountOTPRequest;
import az.iktlab.learnlink.model.otp.UserRecoverAccountRequest;
import az.iktlab.learnlink.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static az.iktlab.learnlink.util.ResponseBuilder.buildResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/public")
public class PublicController {
    private final UserService userService;

    @PostMapping("/recover")
    public ResponseEntity<HttpStatus> sendRecoverMail(@RequestBody @Valid UserRecoverAccountRequest userRecoverAccountRequest) {
        userService.sendOTPForRecover(userRecoverAccountRequest);
        return buildResponse();
    }

    @PostMapping("/recover/otp")
    public ResponseEntity<HttpStatus> useOTPCode(@RequestBody @Valid UserRecoverAccountOTPRequest userRecoverAccountOTPRequest) {
        userService.recoverAccount(userRecoverAccountOTPRequest);
        return buildResponse();
    }
}
