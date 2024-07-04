package com.sample.google_login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {

    private int errorCode;
    private String errorMessage;
}
