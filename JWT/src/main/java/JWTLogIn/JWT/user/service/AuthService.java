package JWTLogIn.JWT.user.service;

import JWTLogIn.JWT.user.entity.Enum.Level;
import JWTLogIn.JWT.user.security.JwtUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class AuthService {
    @Value("${jwt.secret}")
    private String secretKey;

    public Level extractLevel(String token) {
        return JwtUtil.getLevel(token, secretKey);
    }
}
