package com.example.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonApiResponse<T> {

    private String result;

    private T data;

    public static<T> CommonApiResponse<T> success(T data) {
        return new CommonApiResponse<>("success", data);

    }
}
