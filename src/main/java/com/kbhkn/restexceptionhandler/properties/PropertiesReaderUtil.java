package com.kbhkn.restexceptionhandler.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Each micro-service module has different <b>API Name(WhoAmI).</b> Otherwise, default properties are fetched from config-server.
 *
 * @author Hakan KABASAKAL - 8.04.2019 09:33
 */
@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertiesReaderUtil {
    @Value("${spring.application.name}")
    private String api;

    @Value("${custom.api.defaultErrorCode}")
    private String defaultErrorCode;

    @Value("${custom.api.defaultErrorModule}")
    private String defaultErrorModule;

    @Value("${custom.api.defaultErrorMessage}")
    private String defaultErrorMessage;
}
