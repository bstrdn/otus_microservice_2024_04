package ru.bstrdn.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bstrdn.data.dto.BaseResponse;

@RestController
public class HealthController {

  @GetMapping("/health")
  public ResponseEntity<BaseResponse> health() {
    return ResponseEntity.ok(new BaseResponse(HttpStatus.OK));
  }
}
