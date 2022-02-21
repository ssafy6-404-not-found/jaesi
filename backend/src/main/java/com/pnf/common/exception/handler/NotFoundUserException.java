package com.pnf.common.exception.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundUserException extends RuntimeException{
    private ErrorCode errorCode;

    public NotFoundUserException(String msg, ErrorCode errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
