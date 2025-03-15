//✅ 로그인 성공 시 JWT 토큰을 응답
package SWRC.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
}
