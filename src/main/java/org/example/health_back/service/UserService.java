package org.example.health_back.service;
import lombok.RequiredArgsConstructor;
import org.example.health_back.domain.User;
import org.example.health_back.dto.UserDto;
import org.example.health_back.exception.EmailAlreadyExistsException;
import org.example.health_back.exception.UserNotFoundException;
import org.example.health_back.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    public Long addUser(UserDto userDto) {
        User user = User.toUser(userDto);
        user.calculateAge();        // 나이를 계산한다.
        userRepository.save(user);
        return user.getUserId();
    }

    // 로그인
    public Long logInUser(UserDto userDto) {
        User user =
                userRepository
                        .findByUserEmailAndUserPassword(userDto.getUserEmail(), userDto.getUserPassword())
                        .orElseThrow(UserNotFoundException::new);
        return user.getUserId();
    }

    // 겹치치 않는지
    public void validateEmail(String userEmail) throws EmailAlreadyExistsException {
        boolean validation = userRepository
                .existsByUserEmail(userEmail);
        if (validation) { throw new EmailAlreadyExistsException();}

    }
}
