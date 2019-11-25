package com.kbhkn.restexceptionhandler.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This class, contains MFE_Refactoring exceptions configuration.
 * Activated Custom Auto Conf. --> META-INF/spring.factories
 *
 * @author Hakan KABASAKAL - 8.04.2019 10:03
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(value = "com.kbhkn.restexceptionhandler.*")
@EntityScan(basePackages = "com.kbhkn.restexceptionhandler.repository")
@EnableJpaRepositories(basePackages = "com.kbhkn.restexceptionhandler.repository")
public class ExceptionHandlerConfiguration {
}