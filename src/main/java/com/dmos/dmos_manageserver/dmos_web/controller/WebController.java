package com.dmos.dmos_manageserver.dmos_web.controller;

import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_manageserver.dmos_web.component.RegisterService;
import com.dmos.dmos_manageserver.dmos_web.component.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/api/web/queryState")
    public DMOSResponse queryState(@RequestBody @Valid DMOSRequest dmosRequest){
        return storageService.queryState(dmosRequest);
    }
    @PostMapping("/api/web/queryInfo")
    public DMOSResponse queryInfo(@RequestBody @Valid DMOSRequest dmosRequest){
        return registerService.queryInfo(dmosRequest);
    }
    @PostMapping("/api/web/queryStructure")
    public DMOSResponse queryStructure(@RequestBody @Valid DMOSRequest dmosRequest){
        return registerService.queryStructure();
    }
    @PostMapping("/api/web/config")
    public DMOSResponse queryConfig(@RequestBody @Valid DMOSRequest dmosRequest){
        return registerService.config(dmosRequest);
    }
}
