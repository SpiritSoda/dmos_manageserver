package com.dmos.dmos_manageserver.dmos_register.controller;

import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_manageserver.dmos_register.exception.NoTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public DMOSResponse handlerException(Exception e){
        return new DMOSResponse(-1, "发生异常", null);
    }
    @ExceptionHandler(NoTokenException.class)
    public DMOSResponse handlerNoTokenException(NoTokenException e){
        return new DMOSResponse(1, "缺少Token", null);
    }

}