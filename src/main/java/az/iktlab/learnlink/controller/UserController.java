package az.iktlab.learnlink.controller;

import az.iktlab.learnlink.model.jwt.JwtToken;
import az.iktlab.learnlink.model.request.UserSignInRequest;
import az.iktlab.learnlink.model.request.UserSignUpRequest;
import az.iktlab.learnlink.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

import static az.iktlab.learnlink.util.ResponseBuilder.buildResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<JwtToken> signUp(@RequestBody @Valid UserSignUpRequest userSignUpRequest) {
        return buildResponse(userService.signUp(userSignUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtToken> signIn(@RequestBody @Valid UserSignInRequest userSignInRequest) {
        return buildResponse(userService.signIn(userSignInRequest));
    }

    @PostMapping("/balance/{amount}")
    public ResponseEntity<HttpStatus> increaseBalance(Principal principal,
                                                      @PathVariable
                                                      @DecimalMin(value = "10.00", message = "Amount cannot be less than 10.00$")
                                                      @DecimalMax(value = "1000.00", message = "Amount cannot be greater than 1000.00$")
                                                      BigDecimal amount) {
        userService.increaseBalance(amount, principal);
        return buildResponse();

    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(Principal principal) {
        return buildResponse(userService.getBalance(principal));
    }
}
