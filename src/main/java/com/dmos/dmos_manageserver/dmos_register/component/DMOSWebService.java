package com.dmos.dmos_manageserver.dmos_register.component;

import com.dmos.dmos_manageserver.dmos_web.util.STOMPMessage;
import com.dmos.dmos_server.tree.ReportChangeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class DMOSWebService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public DMOSWebService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void reportOnlineOffline(ReportChangeLog changeLog){
        if(!changeLog.hasChange())
            return;
        STOMPMessage stompMessage = STOMPMessage.buildReportMessage(changeLog);
        simpMessagingTemplate.convertAndSend("/api/dmos", stompMessage);
    }
}
