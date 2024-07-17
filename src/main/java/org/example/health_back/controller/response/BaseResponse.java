package org.example.health_back.controller.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseResponse<T> {

    // Getter와 Setter
    private boolean success;
    private String message;
    private T data;

    // 성공 응답 생성자
    public BaseResponse(T data) {
        this.success = true;
        this.message = "성공";
        this.data = data;
    }

    public BaseResponse(T data, String message) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    // 오류 응답 생성자
    public BaseResponse(String message) {
        this.success = false;
        this.message = message;
        this.data = null;
    }

    public BaseResponse() {
        this.success = true;
        this.message = "성공";
        this.data = null;
    }
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(data);
    }

    public static <T> BaseResponse<T> successMemberId(T data) {
        return new BaseResponse<>(data);
    }

    public static <T> BaseResponse<T> ok() {
        return new BaseResponse<>();
    }

    public static <T> BaseResponse<T> success(T data, String message) {
        return new BaseResponse<>(data, message);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(message);
    }
}
