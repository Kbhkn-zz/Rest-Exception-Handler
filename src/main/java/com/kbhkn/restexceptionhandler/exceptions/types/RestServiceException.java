package com.kbhkn.restexceptionhandler.exceptions.types;

import com.kbhkn.restexceptionhandler.exceptions.base.ServiceException;
import lombok.Getter;

/**
 * Each custom exception should be derived from this class.
 *
 * For ex: public enum AuthServiceException implements ServiceException<RestServiceException> {}
 *
 * @author Hakan KABASAKAL - 8.04.2019 09:33
 */
@Getter
public class RestServiceException extends RuntimeException {
    private final String code;
    private final String desc;
    private String module;
    private String whoAmI;

    /**
     * Creates final exception with given data.
     */
    public RestServiceException(ServiceException serviceEx) {
        super(serviceEx.desc());
        this.code = serviceEx.code();
        this.desc = serviceEx.desc();
        this.module = serviceEx.module();
        this.whoAmI = serviceEx.whoAmI();
    }
}
