//✅ 학생 회원가입 시 이메일 인증번호를 포함하여 요청
package SWRC.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String verificationCode; // 이메일 인증번호 추가
}
