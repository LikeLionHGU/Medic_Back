package org.example.health_back.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogInForm {
    private String userEmail;       // 이메일과 비밀번호를 입력함으로써 간다.
    private String userPassword;
}
