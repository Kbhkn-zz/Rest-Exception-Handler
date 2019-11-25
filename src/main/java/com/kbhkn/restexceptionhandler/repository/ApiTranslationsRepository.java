package com.kbhkn.restexceptionhandler.repository;

import java.util.Optional;

/**
 * Get exception descriptions.
 *
 * @author Kbhkn - 8.06.2019 02:22
 */
public interface ApiTranslationsRepository extends ReadOnlyRepository<ApiTranslationsEntity, Long> {
    /**
     * @param module which module in MFE_Refactoring project.
     * @param code search key, in this case error code.
     * @param locale which lang? TR, ENG, IT etc.
     * @return localizations' messages.
     */
    default Optional<ApiTranslationsEntity> findErrorDescription(String module, String code, String locale) {
        return findByApplicationAndModuleAndCodeAndLocale("MFE_Refactoring", module, code, locale);
    }

    /**
     * @param application which application?
     * @param module which module.
     * @param code search key, could be error code, message, i18n apps' keys.
     * @param locale which lang? TR, ENG, IT etc.
     * @return localizations' messages.
     */
    default Optional<ApiTranslationsEntity> findErrorDescription(String application, String module, String code, String locale) {
        return findByApplicationAndModuleAndCodeAndLocale(application, module, code, locale);
    }

    Optional<ApiTranslationsEntity> findByApplicationAndModuleAndCodeAndLocale(String application, String code, String locale, String module);
}

