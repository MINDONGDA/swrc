//✅ 랜덤 6자리 숫자 생성 후 저장 (generateVerificationCode)
//✅ 사용자가 입력한 인증번호 검증 (verifyCode)

package SWRC.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailService {
    private final Map<String, String> verificationCodes = new HashMap<>();

    // ✅ 인증번호 생성 및 저장
    public String generateVerificationCode(String email) {
        String code = String.format("%06d", new Random().nextInt(1000000)); // 6자리 랜덤 숫자 생성
        verificationCodes.put(email, code);
        return code;
    }

    // ✅ 인증번호 검증
    public boolean verifyCode(String email, String inputCode) {
        return verificationCodes.containsKey(email) && verificationCodes.get(email).equals(inputCode);
    }
}
