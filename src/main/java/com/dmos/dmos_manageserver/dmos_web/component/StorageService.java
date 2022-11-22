package com.dmos.dmos_manageserver.dmos_web.component;

import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_manageserver.dmos_storage.service.StateService;
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

    public DMOSResponse queryState(DMOSRequest request){
        List<Integer> ids = (List<Integer>) request.getData().get("ids");
        HashMap<String, Object> data = new HashMap<>();
        for(int id: ids){
            data.put(String.valueOf(id), stateService.findById(id));
        }
        return DMOSResponse.buildSuccessResponse(data);
    }
}
