package com.dmos.dmos_manageserver.dmos_web.component;

import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_common.util.ParseUtil;
import com.dmos.dmos_manageserver.dmos_storage.entity.State;
import com.dmos.dmos_manageserver.dmos_storage.service.StateService;
import com.dmos.dmos_manageserver.dmos_web.dto.QueryDTO;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class StorageService {
    private final StateService stateService;

    @Autowired
    public StorageService(StateService stateService) {
        this.stateService = stateService;
    }

    public DMOSResponse queryState(QueryDTO queryDTO){
        List<Integer> ids = queryDTO.getId();
        HashMap<String, Object> data = new HashMap<>();
        for(int id: ids){
            data.put(String.valueOf(id), State.toDTO(stateService.findById(id)));
        }
        return DMOSResponse.buildSuccessResponse(data);
    }
}
