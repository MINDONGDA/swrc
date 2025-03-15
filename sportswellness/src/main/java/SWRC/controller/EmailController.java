//✅ 이메일 인증번호 전송 (POST /api/auth/email/send)
//✅ 이메일 인증번호 검증 (POST /api/auth/email/verify)
package SWRC.controller;

import SWRC.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    // ✅ 이메일 인증번호 전송
    @PostMapping("/send")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        String code = emailService.generateVerificationCode(email);
        return ResponseEntity.ok("인증번호가 이메일로 전송되었습니다.");
    }

    // ✅ 이메일 인증번호 검증
    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestParam String email, @RequestParam String code) {
        if (emailService.verifyCode(email, code)) {
            return ResponseEntity.ok("이메일 인증 성공!");
        } else {
            return ResponseEntity.badRequest().body("인증번호가 올바르지 않습니다.");
        }
    }
}
