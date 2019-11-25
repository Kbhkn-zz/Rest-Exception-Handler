package com.kbhkn.restexceptionhandler.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Exception messages' structure.
 *
 * @author Hakan KABASAKAL - 8.04.2019 09:33
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionOutput {
    private String api;
    private String errCode;
    private String errDesc;
    private String whoAmI;
    private long timeStamp;
}
