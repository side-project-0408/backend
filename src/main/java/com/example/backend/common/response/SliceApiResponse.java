package com.example.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class SliceApiResponse<T> {

    private HttpStatus result;

    private T data;

    private Boolean hasNext;

}
