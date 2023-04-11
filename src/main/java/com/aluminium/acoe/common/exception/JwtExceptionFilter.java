package com.aluminium.acoe.common.exception;

import com.aluminium.acoe.common.api.code.ErrorCode;
import com.aluminium.acoe.common.api.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * jwt filter에서 발생한 exception에 대한 처리
 * RestControllerAdvice로는 서블릿 Filter에서 발생한 예외를 캐치하지 못함.
 * https://devjem.tistory.com/72
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      try {
          filterChain.doFilter(request, response);
      } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
          log.error(ErrorCode.WRONG_JWT_SIGNATURE.getMessage());
          setErrorResponse(ErrorCode.WRONG_JWT_SIGNATURE, response, e);
      } catch (ExpiredJwtException e) {
          log.error(ErrorCode.EXPIRED_JWT.getMessage());
          setErrorResponse(ErrorCode.EXPIRED_JWT, response, e);
      } catch (UnsupportedJwtException e) {
          log.error(ErrorCode.UNSUPPORTED_JWT.getMessage());
          setErrorResponse(ErrorCode.UNSUPPORTED_JWT, response, e);
      } catch (IllegalArgumentException e) {
          log.error(ErrorCode.ILLEGAL_JWT.getMessage());
          setErrorResponse(ErrorCode.ILLEGAL_JWT, response, e);
      }
    }

    public void setErrorResponse(ErrorCode errorCode, HttpServletResponse response, Throwable ex){
        response.setStatus(errorCode.getStatus());
        response.setContentType("application/json");

        ErrorResponse errorResponse = ErrorResponse.of(errorCode, errorCode.getMessage());

        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), errorResponse);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
