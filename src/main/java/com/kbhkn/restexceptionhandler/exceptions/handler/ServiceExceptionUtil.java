package com.kbhkn.restexceptionhandler.exceptions.handler;

import com.kbhkn.restexceptionhandler.exceptions.model.ExceptionOutput;
import com.kbhkn.restexceptionhandler.exceptions.types.RemoteRestServiceException;
import com.kbhkn.restexceptionhandler.exceptions.types.RestServiceException;
import com.kbhkn.restexceptionhandler.properties.PropertiesReaderUtil;
import com.kbhkn.restexceptionhandler.repository.ApiTranslationsEntity;
import com.kbhkn.restexceptionhandler.repository.ApiTranslationsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

/**
 * Prepares of exception responses!
 *
 * @author Hakan KABASAKAL - 8.04.2019 09:33
 */

@Slf4j
@Component
@AllArgsConstructor
class ServiceExceptionUtil {
    private final PropertiesReaderUtil properties;
    private final ApiTranslationsRepository apiTranslationsRepository;

    ExceptionOutput prepareExceptionOutput(String lang) {
        return ExceptionOutput.builder()
                .api(properties.getApi())
                .errCode(properties.getDefaultErrorCode())
                .errDesc(useDefaultErrorDefinition(lang))
                .timeStamp(Instant.now().toEpochMilli())
                .build();
    }

    ExceptionOutput prepareExceptionOutput(RemoteRestServiceException ex) {
        return ExceptionOutput.builder()
                .api(ex.getApi())
                .whoAmI(ex.getWhoAmI())
                .errCode(ex.getCode())
                .errDesc(ex.getDesc())
                .timeStamp(ex.getTimeStamp() > 0 ? ex.getTimeStamp() : Instant.now().toEpochMilli())
                .build();
    }

    ExceptionOutput prepareExceptionOutput(RestServiceException ex, String lang) {
        return prepareExceptionOutput(ex.getCode(), ex.getModule(), ex.getWhoAmI(), lang);
    }

    /**
     * Fetches i18n error descriptions by error-code from the database.
     *
     * @param orjErrCode error code
     * @param module     which micro-service throws the exception.
     * @param lang       application language.
     * @return prepared exception
     */
    private ExceptionOutput prepareExceptionOutput(String orjErrCode, String module, String whoAmI, String lang) {
        String errCode = Optional.ofNullable(orjErrCode).orElse(properties.getDefaultErrorCode());
        Optional<ApiTranslationsEntity> exceptionDescription = apiTranslationsRepository.findErrorDescription(module, errCode, lang);

        String errDesc;
        String prefix = "";
        if (exceptionDescription.isPresent()) {
            errDesc = exceptionDescription.get().getValue();
        } else {
            log.error("Could not find exception definition for module: {} code: {}", module, errCode);
            if (!properties.getDefaultErrorCode().equalsIgnoreCase(errCode)) {
                prefix = "ND_"; //Exception is already exist and the exception is not defined(ND) in the api_translation_table.
            }

            errDesc = useDefaultErrorDefinition(lang);
        }

        return ExceptionOutput.builder()
                .api(properties.getApi())
                .errCode(errCode)
                .errDesc(errDesc)
                .whoAmI(prefix + whoAmI)
                .timeStamp(Instant.now().toEpochMilli())
                .build();
    }

    /**
     * Default strategy to apply when error code is not found in database.
     *
     * @param lang application language.
     * @return default error description to use
     */
    private String useDefaultErrorDefinition(String lang) {
        String defaultErrorModule = properties.getDefaultErrorModule();
        String defaultErrorCode = properties.getDefaultErrorCode();

        Optional<ApiTranslationsEntity> excDescription =
                apiTranslationsRepository.findErrorDescription(defaultErrorModule, defaultErrorCode, lang);

        if (excDescription.isPresent()) {
            return excDescription.get().getValue();
        } else {
            log.info("Could not find default exception definition for api: {} module: {}, code: {}",
                    properties.getApi(), defaultErrorModule, defaultErrorCode);

            return properties.getDefaultErrorMessage();
        }
    }
}
