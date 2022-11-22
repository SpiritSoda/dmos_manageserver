package com.dmos.dmos_manageserver.dmos_storage.controller;

import com.dmos.dmos_common.data.ClientReportDTO;
import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_manageserver.dmos_storage.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
public class StorageController {
    private final StateService stateService;

    @Autowired
    public StorageController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/api/storage/report")
    public DMOSResponse report(@RequestBody @Valid DMOSRequest dmosRequest, HttpServletRequest request){
        ClientReportDTO reportDTO = (ClientReportDTO) dmosRequest.getData().get("report");
        stateService.update(reportDTO);
        // NONE
        return DMOSResponse.buildSuccessResponse(null);
    }
    @PostMapping("/api/storage/queryState")
    public DMOSResponse queryState(@RequestBody @Valid DMOSRequest dmosRequest){
        List<Integer> ids = (List<Integer>) dmosRequest.getData().get("ids");
        HashMap<String, Object> data = new HashMap<>();
        for(int id: ids){
            data.put(String.valueOf(id), stateService.findById(id));
        }
        return DMOSResponse.buildSuccessResponse(data);
    }
}
