package com.dmos.dmos_manageserver.dmos_web.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class STOMPMessage {
    public static int ONLINE_OFFLINE_REPORT = 0;

    private int type;
    private Object data;

    public static STOMPMessage buildReportMessage(Object payload){
        return new STOMPMessage(
                ONLINE_OFFLINE_REPORT,
                payload
        );
    }
}
