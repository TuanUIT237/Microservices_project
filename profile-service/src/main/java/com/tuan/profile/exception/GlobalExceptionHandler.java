 package com.tuan.profile.exception;

 import org.springframework.http.ResponseEntity;
 import org.springframework.security.access.AccessDeniedException;
 import org.springframework.web.bind.annotation.ControllerAdvice;
 import org.springframework.web.bind.annotation.ExceptionHandler;

 import com.tuan.profile.dto.APIRespone.ApiResponse;

 import lombok.extern.slf4j.Slf4j;

 @ControllerAdvice
 @Slf4j
 public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> hanldingRunTimeException(RuntimeException exception) throws Exception {
        ApiResponse respone = new ApiResponse();
        respone.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        respone.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(respone);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> hanldingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse respone = new ApiResponse();
        respone.setCode(errorCode.getCode());
        respone.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(respone);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> hanldingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }
 }
