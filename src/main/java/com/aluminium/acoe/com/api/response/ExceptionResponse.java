package com.aluminium.acoe.com.api.response;

import com.aluminium.acoe.com.api.code.ExceptionCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

/**
 * GlobalExceptionHandler에서 발생한 에러에 대한 응답 처리를 관리
 * 클라이언트에서 요청한 값에 대해 '실패한 응답' 반환 값의 형태를 구성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExceptionResponse {
  private int status;                 // 에러 상태 코드
  private String divisionCode;        // 에러 구분 코드
  private String resultMsg;           // 에러 메시지
  private List<FieldError> errors;    // 상세 에러 메시지
  private String reason;              // 에러 이유

  /**
   * ExceptionResponse 생성자-1
   *
   * @param code ErrorCode
   */
  @Builder
  protected ExceptionResponse(final ExceptionCode code) {
    this.resultMsg = code.getMessage();
    this.status = code.getStatus();
    this.divisionCode = code.getDivisionCode();
    this.errors = new ArrayList<>();
  }

  /**
   * ExceptionResponse 생성자-2
   *
   * @param code   ErrorCode
   * @param reason String
   */
  @Builder
  protected ExceptionResponse(final ExceptionCode code, final String reason) {
    this.resultMsg = code.getMessage();
    this.status = code.getStatus();
    this.divisionCode = code.getDivisionCode();
    this.reason = reason;
  }

  /**
   * ExceptionResponse 생성자-3
   *
   * @param code   ErrorCode
   * @param errors List<FieldError>
   */
  @Builder
  protected ExceptionResponse(final ExceptionCode code, final List<FieldError> errors) {
    this.resultMsg = code.getMessage();
    this.status = code.getStatus();
    this.errors = errors;
    this.divisionCode = code.getDivisionCode();
  }


  /**
   * Global Exception 전송 타입-1
   *
   * @param code          ErrorCode
   * @param bindingResult BindingResult
   * @return ExceptionResponse
   */
  public static ExceptionResponse of(final ExceptionCode code, final BindingResult bindingResult) {
    return new ExceptionResponse(code, FieldError.of(bindingResult));
  }

  /**
   * Global Exception 전송 타입-2
   *
   * @param code ErrorCode
   * @return ExceptionResponse
   */
  public static ExceptionResponse of(final ExceptionCode code) {
    return new ExceptionResponse(code);
  }

  /**
   * Global Exception 전송 타입-3
   *
   * @param code   ErrorCode
   * @param reason String
   * @return ExceptionResponse
   */
  public static ExceptionResponse of(final ExceptionCode code, final String reason) {
    return new ExceptionResponse(code, reason);
  }


  /**
   * 에러를 e.getBindingResult() 형태로 전달 받는 경우 해당 내용을 상세 내용으로 변경하는 기능을 수행한다.
   */
  @Getter
  public static class FieldError {
    private final String field;
    private final String value;
    private final String reason;

    public static List<FieldError> of(final String field, final String value, final String reason) {
      List<FieldError> fieldErrors = new ArrayList<>();
      fieldErrors.add(new FieldError(field, value, reason));
      return fieldErrors;
    }

    private static List<FieldError> of(final BindingResult bindingResult) {
      final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
      return fieldErrors.stream()
          .map(error -> new FieldError(
              error.getField(),
              error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
              error.getDefaultMessage()))
          .collect(Collectors.toList());
    }

    @Builder
    FieldError(String field, String value, String reason) {
      this.field = field;
      this.value = value;
      this.reason = reason;
    }
  }
}