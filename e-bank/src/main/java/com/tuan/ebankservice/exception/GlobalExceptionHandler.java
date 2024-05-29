package com.tuan.ebankservice.exception;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.ConstraintViolation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tuan.ebankservice.dto.apiresponse.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String[] ATTRIBUTE_NAMES = {"min", "value"};

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> hanldingRunTimeException(RuntimeException exception) throws Exception {
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> hanldingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse respone = new ApiResponse();
        respone.setCode(errorCode.getCode());
        respone.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(respone);
    }

    //    @ExceptionHandler(value = AccessDeniedException.class)
    //    ResponseEntity<ApiResponse> hanldingAccessDeniedException(AccessDeniedException exception) {
    //        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
    //        return ResponseEntity.status(errorCode.getStatusCode())
    //                .body(ApiResponse.builder()
    //                        .status("failed")
    //                        .message(errorCode.getMessage())
    //                        .build());
    //    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> hanldingValidation(MethodArgumentNotValidException exception) {
        String enumkey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumkey);
            var constraintViolation =
                    exception.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class);
            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
        } catch (IllegalArgumentException e) {

        }

        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(
                Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage(), attributes)
                        : errorCode.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        List<String> valueAttributes = Arrays.stream(ATTRIBUTE_NAMES)
                .filter(value -> attributes.get(value) != null)
                .toList();
        if (valueAttributes.isEmpty()) return message;
        else {
            String name = valueAttributes.get(0);
            String value = String.valueOf(attributes.get(name));
            return message.replace("{" + name + "}", value);
        }
    }
}
