package org.example.health_back.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor        // 인자가 없는 생성자라릌 클래스에 대해 생성한다.
public class SignUpForm {

    private String userPassword;
    private String userEmail;
    private String userNickname;
    private boolean gender;     // true이면 여자 false이면 남자
    private String birthDigit;      // 6자리여야 한다.
    private List<String> healthHashTag;      // 리스트로 받는다.
    private List<String> drugInUse;      // 현재 복용중인 약, 리스트로 받는다. 1개 이상
}
