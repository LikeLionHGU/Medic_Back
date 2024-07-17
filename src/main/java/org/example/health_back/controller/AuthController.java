package org.example.health_back.controller;

import lombok.RequiredArgsConstructor;
import org.example.health_back.controller.form.LogInForm;
import org.example.health_back.controller.form.SignUpForm;
import org.example.health_back.controller.response.BaseResponse;
import org.example.health_back.dto.UserDto;
import org.example.health_back.exception.EmailAlreadyExistsException;
import org.example.health_back.exception.UserNotFoundException;
import org.example.health_back.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/sign-up")
    public BaseResponse<?> signUp(@RequestBody SignUpForm signUpForm) {           // 요청 본문에 있는 데이터를 SignUpForm 객체로 바꾼다.
        Long userId = userService.addUser(UserDto.from(signUpForm));
        return BaseResponse.success(userId);
    }

    // 로그인
    @PostMapping("/log-in")
    public BaseResponse<?> logIn(@RequestBody LogInForm form) {
        try {
            Long userId = userService.logInUser(UserDto.from(form));
            return BaseResponse.success(userId);

        } catch(UserNotFoundException exception){
            return BaseResponse.error("User not found");
        }
    }

    // 겹치치 않는지 확인
    @GetMapping("/sign-up/validation")
    public BaseResponse<?> validateEmail(@RequestParam String userEmail) throws EmailAlreadyExistsException {
        try{
            userService.validateEmail(userEmail);
            return BaseResponse.ok();
        } catch(EmailAlreadyExistsException exception){
            return BaseResponse.error("이미 존재하는 이메일입니다.");
        }
    }
}
