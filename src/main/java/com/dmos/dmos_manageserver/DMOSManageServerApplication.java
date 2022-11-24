package com.dmos.dmos_manageserver;

import com.dmos.dmos_manageserver.dmos_register.component.DMOSPoolRegisterServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class DMOSManageServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DMOSManageServerApplication.class, args);
        DMOSPoolRegisterServer.getSingle().run();
    }
}
