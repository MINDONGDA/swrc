// 2. 핵심 비즈니스 로직
package SWRC.service;

import SWRC.dto.request.AdminSignupRequest;
import SWRC.dto.request.PasswordChangeRequest;
import SWRC.dto.request.SignupRequest;
import SWRC.entity.Admin;
import SWRC.entity.User;
import SWRC.repository.AdminRepository;
import SWRC.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // ✅ 로그인 검증 로직
    public boolean loginUser(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }

    // ✅ 학생 회원가입
    public void registerUser(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .role(User.Role.STUDENT) // 기본값: 학생
                .build();

        userRepository.save(user);
    }

    // ✅ 관리자 회원가입
    public void registerAdmin(AdminSignupRequest request) {
        if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        Admin admin = Admin.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .sportType(request.getSportType()) // ✅ 종목 저장
                .role(User.Role.ADMIN) // ✅ 역할: 관리자
                .build();

        adminRepository.save(admin);
    }

    // ✅ 비밀번호 변경 로직 추가
    public void updatePassword(PasswordChangeRequest request) {
        // 인증번호 확인
        if (!emailService.verifyCode(request.getEmail(), request.getVerificationCode())) {
            throw new RuntimeException("인증번호가 올바르지 않습니다.");
        }

        // 비밀번호 일치 확인
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 사용자 찾기
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("해당 이메일의 사용자를 찾을 수 없습니다.");
        }

        // 비밀번호 변경
        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
