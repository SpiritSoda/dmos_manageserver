package com.dmos.dmos_manageserver.dmos_storage.controller;

import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public class StorageController {
    @PostMapping("/api/storage/report")
    public DMOSResponse report(@RequestBody @Valid DMOSRequest dmosRequest, HttpServletRequest request){
        // NONE
        return DMOSResponse.buildSuccessResponse(null);
    }
}
