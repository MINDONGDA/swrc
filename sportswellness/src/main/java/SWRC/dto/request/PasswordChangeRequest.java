//✅ 비밀번호 변경 요청 시 이메일, 인증번호, 새 비밀번호 2회 입력
package SWRC.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangeRequest {
    private String email;
    private String verificationCode;
    private String newPassword;
    private String confirmPassword;
}
