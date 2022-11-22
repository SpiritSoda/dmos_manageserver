package com.dmos.dmos_manageserver.dmos_register.component;

import com.dmos.dmos_common.util.Port;
import com.dmos.dmos_manageserver.dmos_register.handler.DMOSRegisterServerHandler;
import com.dmos.dmos_server.DMOSServer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

@Component
public class DMOSRegisterServerSide {
    @PostConstruct
    public void start(){
        DMOSServer server = new DMOSServer(new InetSocketAddress("127.0.0.1", Port.REGISTER_CHANNEL_PORT), new DMOSRegisterServerHandler());
        server.start();
    }
}
