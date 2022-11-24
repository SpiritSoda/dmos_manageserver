package com.dmos.dmos_manageserver.dmos_web.controller;

import com.dmos.dmos_common.data.ConfigDTO;
import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_manageserver.dmos_web.component.RegisterService;
import com.dmos.dmos_manageserver.dmos_web.component.StorageService;
import com.dmos.dmos_manageserver.dmos_web.dto.QueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
public class WebController {
    private final StorageService storageService;
    private final RegisterService registerService;

    @Autowired
    public WebController(StorageService storageService, RegisterService registerService) {
        this.storageService = storageService;
        this.registerService = registerService;
    }

    @PostMapping("/api/getNodeList")
    public DMOSResponse queryState(@RequestBody @Valid QueryDTO queryDTO){
        return storageService.queryState(queryDTO);
    }
    @PostMapping("/api/getNodeInfo")
    public DMOSResponse queryInfo(@RequestBody @Valid QueryDTO queryDTO){
        return registerService.queryInfo(queryDTO);
    }
    @GetMapping("/api/getTreeDiagram")
    public DMOSResponse queryStructure(){
        return registerService.queryStructure();
    }
    @PostMapping("/api/configure")
    public DMOSResponse config(@RequestBody @Valid ConfigDTO configDTO){
        return registerService.config(configDTO);
    }
}
