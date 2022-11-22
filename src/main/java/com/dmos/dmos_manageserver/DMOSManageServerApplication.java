package com.dmos.dmos_manageserver;

import com.dmos.dmos_manageserver.dmos_register.component.DMOSPoolRegisterServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DMOSManageServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DMOSManageServerApplication.class, args);
        DMOSPoolRegisterServer.getSingle().run();
    }
}
