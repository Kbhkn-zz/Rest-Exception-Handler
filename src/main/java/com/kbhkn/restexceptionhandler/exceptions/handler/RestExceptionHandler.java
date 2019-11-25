package com.kbhkn.restexceptionhandler.exceptions.handler;

import com.kbhkn.restexceptionhandler.exceptions.model.ExceptionOutput;
import com.kbhkn.restexceptionhandler.exceptions.types.RemoteRestServiceException;
import com.kbhkn.restexceptionhandler.exceptions.types.RestServiceException;
import com.kbhkn.restexceptionhandler.properties.PropertiesReaderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;
import java.util.Optional;

/**
 * Global exception handler for MFE_Refactoring Project
 *
 * @author Hakan KABASAKAL - 8.04.2019 09:33
 */

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final PropertiesReaderUtil properties;
    private final ServiceExceptionUtil exceptionUtil;

    /**
     * Catches UserServiceException(For exp: CaServiceException.class) than gets error description from the database by error code.
     *
     * @param ex      exception
     * @param request app request
     * @return prepared exception response.
     */
    @ExceptionHandler(value = {RestServiceException.class})
    protected ResponseEntity<Object> handleMFERefactoringException(RestServiceException ex, WebRequest request) {
        log.error("Exception Handled, Type: MFE_Refactoring, Api: {}, module: {}", properties.getApi() + " - " + ex.getWhoAmI(), ex.getModule(), ex);

        String locale = getApplicationLanguage(request);

        ExceptionOutput exceptionOutput = exceptionUtil.prepareExceptionOutput(ex, locale);

        return handleExceptionInternal(ex, exceptionOutput, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED, request);
    }

    /**
     * Catches RemoteRestServiceException. RemoteRestServiceException is a Feing-Exceptions.
     * So, before throws from the feign calls, exception is already prepared. Just passing data and throws again.
     *
     * @param ex      exception
     * @param request Users' request.
     * @return prepared exception response.
     */
    @ExceptionHandler(value = {RemoteRestServiceException.class})
    protected ResponseEntity<Object> handleRemoteServiceException(RemoteRestServiceException ex, WebRequest request) {
        log.error("Exception Handled, Type: Remote Service - Feign, Api: {}", ex.getApi() + " - " + ex.getWhoAmI(), ex);

        ExceptionOutput exceptionOutput = exceptionUtil.prepareExceptionOutput(ex);

        return handleExceptionInternal(ex, exceptionOutput, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED, request);
    }

    /**
     * Generic exception.
     *
     * @param ex      Any Exception except, MFE_Refactoring and RemoteRestService.
     * @param request Users' request.
     * @return prepared exception response.
     */
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
        log.error("Exception Handled, Type: Exception.class, Api: {}", properties.getApi(), ex);

        String locale = getApplicationLanguage(request);

        ExceptionOutput exceptionOutput = exceptionUtil.prepareExceptionOutput(locale);

        return handleExceptionInternal(ex, exceptionOutput, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * @param request users' request.
     * @return find users' app lang. if could not find the lang. returns TR.
     */
    private String getApplicationLanguage(WebRequest request) {
        return Optional.ofNullable(request.getLocale())
                .orElse(Locale.forLanguageTag("tr-TR"))
                .getLanguage()
                .toUpperCase();
    }
}
