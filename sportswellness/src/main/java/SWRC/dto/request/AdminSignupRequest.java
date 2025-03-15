//✅ 관리자는 "종목 선택" 필드를 추가하여 회원가입 진행
package SWRC.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminSignupRequest {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String verificationCode; // 이메일 인증번호 추가
    private String sportType; // 관리자 종목 추가
}
