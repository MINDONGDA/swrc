// 1. 클라이언트 요청 처리
package SWRC.controller;

import SWRC.dto.request.LoginRequest;
import SWRC.dto.response.LoginResponse;
import SWRC.dto.request.SignupRequest;
import SWRC.dto.request.AdminSignupRequest;
import SWRC.service.UserService;
import SWRC.service.EmailService;
import SWRC.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    // ✅ 로그인 API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (userService.loginUser(request.getEmail(), request.getPassword())) {
            String token = jwtUtil.generateToken(request.getEmail());
            return ResponseEntity.ok(new LoginResponse(token));
        } else {
            return ResponseEntity.badRequest().body("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    // ✅ 학생 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        if (!emailService.verifyCode(request.getEmail(), request.getVerificationCode())) {
            return ResponseEntity.badRequest().body("이메일 인증이 완료되지 않았습니다.");
        }
        userService.registerUser(request);
        return ResponseEntity.ok("회원가입 성공!");
    }

    // ✅ 관리자 회원가입 API
    @PostMapping("/signup/admin")
    public ResponseEntity<String> signupAdmin(@RequestBody AdminSignupRequest request) {
        if (!emailService.verifyCode(request.getEmail(), request.getVerificationCode())) {
            return ResponseEntity.badRequest().body("이메일 인증이 완료되지 않았습니다.");
        }
        userService.registerAdmin(request);
        return ResponseEntity.ok("관리자 회원가입 성공!");
    }
}
