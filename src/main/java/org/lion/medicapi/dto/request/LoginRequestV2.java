package org.lion.medicapi.dto.request;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class LoginRequestV2 {

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{1,8}", message = "비밀번호는 영문과 숫자가 포함된 1자 ~ 8자의 비밀번호여야 합니다.")
    private String password;
}
