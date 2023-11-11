package az.iktlab.learnlink.security;

import az.iktlab.learnlink.constants.SecurityConstants;
import az.iktlab.learnlink.model.JwtToken;
import az.iktlab.learnlink.util.DateHelper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JWTProvider {

    private final JWTVerifier jwtVerifier;
    private final SecurityConstants securityConstants;

    public JWTProvider(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
        this.jwtVerifier = JWT.require(Algorithm.HMAC256(securityConstants.getSecretKey()))
                .withSubject("Course Management")
                .withIssuer("Course-Management-System")
                .build();
    }

    public JwtToken getJWTToken(String userId) {
        Date createDate = DateHelper.now();
        Date expirationDate = getExpirationDate();
        String jwtToken = JWT
                .create()
                .withSubject("Course Management")
                .withExpiresAt(expirationDate)
                .withIssuedAt(createDate)
                .withClaim("userId", userId)
                .withIssuer("Course-Management-System")
                .sign(Algorithm.HMAC256(securityConstants.getSecretKey()));

        return JwtToken.builder()
                .token(jwtToken)
                .createDate(createDate.getTime())
                .expirationDate(expirationDate.getTime())
                .build();
    }

    public String extractUserId(String token) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaim("userId").asString();
    }



    private Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 3);
        return calendar.getTime();
    }

}
