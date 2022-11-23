package com.dmos.dmos_manageserver.dmos_web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class STOMPController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

}
