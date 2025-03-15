//✅ 비밀번호 변경 요청 시 이메일을 입력받음
package SWRC.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetRequest {
    private String email;
}
