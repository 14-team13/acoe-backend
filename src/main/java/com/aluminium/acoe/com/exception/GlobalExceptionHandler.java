package com.aluminium.acoe.com.exception;

import com.aluminium.acoe.com.api.code.ExceptionCode;
import com.aluminium.acoe.com.api.response.ExceptionResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Controller 내에서 발생하는 Exception 대해서 Catch 하여 응답값(Response)을 보내주는 기능을 수행함.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final HttpStatus HTTP_STATUS_OK = HttpStatus.OK;

    /**
     * [Exception] API 호출 시 '객체' 혹은 '파라미터' 데이터 값이 유효하지 않은 경우
     *
     * @param ex MethodArgumentNotValidException
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException", ex);
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append(fieldError.getField()).append(":");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(", ");
        }
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.NOT_VALID_ERROR, String.valueOf(stringBuilder));
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * [Exception] API 호출 시 'Header' 내에 데이터 값이 유효하지 않은 경우
     *
     * @param ex MissingRequestHeaderException
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<ExceptionResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error("MissingRequestHeaderException", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * [Exception] 클라이언트에서 Body로 '객체' 데이터가 넘어오지 않았을 경우
     *
     * @param ex HttpMessageNotReadableException
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * [Exception] 클라이언트에서 request로 '파라미터로' 데이터가 넘어오지 않았을 경우
     *
     * @param ex MissingServletRequestParameterException
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ExceptionResponse> handleMissingRequestHeaderExceptionException(
        MissingServletRequestParameterException ex) {
        log.error("handleMissingServletRequestParameterException", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.MISSING_REQUEST_PARAMETER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * [Exception] 잘못된 서버 요청일 경우 발생한 경우
     *
     * @param e HttpClientErrorException
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    protected ResponseEntity<ExceptionResponse> handleBadRequestException(HttpClientErrorException e) {
        log.error("HttpClientErrorException.BadRequest", e);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.BAD_REQUEST_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }


    /**
     * [Exception] 잘못된 주소로 요청 한 경우
     *
     * @param e NoHandlerFoundException
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleNoHandlerFoundExceptionException(NoHandlerFoundException e) {
        log.error("handleNoHandlerFoundExceptionException", e);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.NOT_FOUND_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }


    /**
     * [Exception] NULL 값이 발생한 경우
     *
     * @param e NullPointerException
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException e) {
        log.error("handleNullPointerException", e);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.NULL_POINT_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * Input / Output 내에서 발생한 경우
     *
     * @param ex IOException
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ExceptionResponse> handleIOException(IOException ex) {
        log.error("handleIOException", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.IO_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }


    /**
     * com.google.gson 내에 Exception 발생하는 경우
     *
     * @param ex JsonParseException
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(JsonParseException.class)
    protected ResponseEntity<ExceptionResponse> handleJsonParseExceptionException(JsonParseException ex) {
        log.error("handleJsonParseExceptionException", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.JSON_PARSE_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * com.fasterxml.jackson.core 내에 Exception 발생하는 경우
     *
     * @param ex JsonProcessingException
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<ExceptionResponse> handleJsonProcessingException(JsonProcessingException ex) {
        log.error("handleJsonProcessingException", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    // =========  Business Exception ===============

    /**
     * [Exception] 로그인 not found member  Excetpion
     *
     * @param ex NotFoundMemberException
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(NotFoundMemberException.class)
    protected ResponseEntity<ExceptionResponse> handleNotFoundMemberException(NotFoundMemberException ex) {
        log.error("handleNotFoundMemberException", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.NOT_FOUND_MEMBER, ex.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * [Exception] 로그인 duplicate member  Excetpion
     *
     * @param ex NotFoundMemberException
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(DuplicateMemberException.class)
    protected ResponseEntity<ExceptionResponse> handleDuplicateMemberException(DuplicateMemberException ex) {
        log.error("handleDuplicateMemberException", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.DUPLICATE_MEMBER, ex.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    // ============= 최상위 Exception ===================

    /**
     * [Exception] 모든 Exception 경우 발생
     *
     * @param ex Exception
     * @return ResponseEntity<ExceptionResponse>
     */
    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex) {
        log.error("Exception", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionCode.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }
}