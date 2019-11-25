package com.kbhkn.restexceptionhandler.repository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * Values should be added from the database or admin-panel etc.
 *
 * The combination of application_name, module, code and locale should have a unique constraint.
 *
 * Means of columns.
 *      application --> which application? For example: MFE_Refactoring
 *      module --> which micro-service? For example: auth
 *      code --> code of exception should be use with module and application.
 *      locale --> error lang, For example: TR, ENG
 *      value --> value of that row. For example --> Username or password is incorrect.
 *      description --> Really Means of exception.
 *
 *      Example Row:
 *
 *      ID        APPLICATION       MODULE        CODE               LOCALE               VALUE                         DESCRIPTION
 *      100    MFE_Refactoring      auth     password.error            ENG      Username or password is incorrect.   Wrong password.
 *      101    MFE_Refactoring      auth     password.expire.error     ENG      Your password's expired.             Password must change.
 *      200    MFE_Refactoring     person    usernotfound.error        ENG      User does not exist in the system.   User does not found.
 *      201    MFE_Refactoring     person    24hourblockeduser.error   ENG      User is blocked.                     User is temporarily blocked.
 *
 * PS;
 *      select * from Api_Translations;
 *
 * @author Hakan KABASAKAL - 5.04.2019 10:30
 */
@Entity
@Immutable // Read-Only Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE) // Do not create an instance!
@Table(
        name = "Api_Translations",
        uniqueConstraints = @UniqueConstraint(columnNames={"application", "module", "code", "locale"})
)
public class ApiTranslationsEntity {
    @Id @Column
    private Long id;

    @Column(nullable = false)
    private String application;

    @Column(nullable = false)
    private String module;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String locale;

    @Column(nullable = false)
    private String description;

    @Getter
    @Column(nullable = false)
    private String value;
}
