package org.example.health_back.exception;


public class EmailAlreadyExistsException extends Throwable {

    private static final String MESSAGE = "이미 존재하는 이메일입니다.";

    public EmailAlreadyExistsException() {
        super(MESSAGE);
    }
}
