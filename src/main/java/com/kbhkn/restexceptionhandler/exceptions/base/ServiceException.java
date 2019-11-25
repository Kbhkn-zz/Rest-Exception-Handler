package com.kbhkn.restexceptionhandler.exceptions.base;

/**
 *
 * Custom exceptions uses this interface.
 *
 * @author Hakan KABASAKAL - 8.04.2019 09:33
 */

public interface ServiceException<T extends RuntimeException> {
    /**
     * @return Specific error code as a string for the exception.
     */
    String code();

    /**
     * @return description of the exception.
     */
    String desc();

    /**
     * @return module name
     */
    String module();

    /**
     * Returns prefix name of the exception.
     *
     * <p>It is intended to give developer a hint, which service throw the exception.</p>
     *
     * @return micro-service name
     */
    String whoAmI();

    /**
     * Helper method to throw the exception itself.
     */
    default void raise() { throw exception(); }

    /**
     * @return the enum exception as Java exception itself.
     */
    T exception();
}
