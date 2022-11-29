package com.dmos.dmos_manageserver.dmos_storage.controller;

import com.dmos.dmos_common.data.ClientReportDTO;
import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_common.util.ParseUtil;
import com.dmos.dmos_manageserver.dmos_storage.entity.State;
import com.dmos.dmos_manageserver.dmos_storage.service.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class StorageController {
    private final StateService stateService;

    @Autowired
    public StorageController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/storage/report")
    public DMOSResponse report(@RequestBody @Valid DMOSRequest dmosRequest, HttpServletRequest request){
        ClientReportDTO reportDTO = ParseUtil.parse(dmosRequest.getData().get("report"), ClientReportDTO.class);
        stateService.update(reportDTO);
        // NONE
        return DMOSResponse.buildSuccessResponse(null);
    }
    @PostMapping("/storage/queryState")
    public DMOSResponse queryState(@RequestBody @Valid DMOSRequest dmosRequest){
        List<Integer> ids = (List<Integer>) dmosRequest.getData().get("ids");
        HashMap<String, Object> data = new HashMap<>();
        for(int id: ids){
            data.put(String.valueOf(id), State.toDTO(stateService.findById(id)));
        }
        return DMOSResponse.buildSuccessResponse(data);
    }
}
