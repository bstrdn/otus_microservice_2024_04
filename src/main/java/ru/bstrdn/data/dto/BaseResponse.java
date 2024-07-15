package ru.bstrdn.data.dto;

import org.springframework.http.HttpStatus;

public record BaseResponse(HttpStatus status) {}
