package com.kbhkn.restexceptionhandler.exceptions.types;


import lombok.Getter;

/**
 * Each service should throw the same responses!
 * Do not do something. Feing-Parser knows what to do!
 *
 * @author Hakan KABASAKAL - 8.04.2019 11:03
 */
@Getter
public class RemoteRestServiceException extends RuntimeException {
    private final String code;
    private final String desc;
    private final String api;
    private final String whoAmI;
    private final long timeStamp;

    /**
     * Creates final exception with given data.
     */
    public RemoteRestServiceException(String code, String desc, String api, String whoAmI, Long timeStamp) {
        super(desc);
        this.code = code;
        this.desc = desc;
        this.api = api;
        this.whoAmI = whoAmI;
        this.timeStamp = timeStamp;
    }
}
