//✅ POST /api/auth/password/reset-request → 이메일로 인증번호 전송
//✅ POST /api/auth/password/verify → 입력한 인증번호 검증
//✅ POST /api/auth/password/change → 비밀번호 변경
package SWRC.controller;

import SWRC.dto.request.PasswordChangeRequest;
import SWRC.service.EmailService;
import SWRC.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/password")
@RequiredArgsConstructor
public class PasswordController {
    private final EmailService emailService;
    private final UserService userService;

    // ✅ 1. 비밀번호 변경 요청 (이메일로 인증번호 전송)
    @PostMapping("/reset-request")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        String code = emailService.generateVerificationCode(email);
        return ResponseEntity.ok("비밀번호 변경 인증번호가 이메일로 전송되었습니다.");
    }

    // ✅ 2. 인증번호 확인
    @PostMapping("/verify")
    public ResponseEntity<String> verifyResetCode(@RequestParam String email, @RequestParam String code) {
        if (emailService.verifyCode(email, code)) {
            return ResponseEntity.ok("인증번호 확인 완료! 새 비밀번호를 입력하세요.");
        } else {
            return ResponseEntity.badRequest().body("인증번호가 올바르지 않습니다.");
        }
    }

    // ✅ 3. 비밀번호 변경
    @PostMapping("/change")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request) {
        userService.updatePassword(request);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }
}
