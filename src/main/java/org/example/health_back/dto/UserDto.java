package org.example.health_back.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.health_back.controller.form.LogInForm;
import org.example.health_back.controller.form.SignUpForm;

import java.util.List;

//UserDto는 데이터 전송 객체로서, 서비스 계층과 데이터 계층 간의 데이터 교환에 사용됩니다.
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;
    private String userPassword;
    private String userEmail;
    private String userNickname;
    private boolean gender;     // 1이면 남자 0이면 여자
    private String birthDigit;
    private List<String> healthHashTag;      // 리스트로 받는다.
    private List<String> drugInUse;      // 복용중인 약.

    // 회원가입하면서 받아온 데이터를 DTO 형식으로 바꾸어준다.
    public static UserDto from(SignUpForm signUpForm) {
        return UserDto.builder()
                .userNickname(signUpForm.getUserNickname())
                .userPassword(signUpForm.getUserPassword())
                .userEmail(signUpForm.getUserEmail())
                .gender(signUpForm.isGender())
                .birthDigit(signUpForm.getBirthDigit())
                .healthHashTag(signUpForm.getHealthHashTag())
                .drugInUse(signUpForm.getDrugInUse())
                .build();
    }

    public static UserDto from(LogInForm logInForm) {
        return UserDto.builder()
                .userEmail(logInForm.getUserEmail())
                .userPassword(logInForm.getUserPassword()) // 인스턴스 메서드 호출
                .build();
    }
}
