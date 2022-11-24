package com.dmos.dmos_manageserver.dmos_web.component;

import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_common.util.ParseUtil;
import com.dmos.dmos_manageserver.dmos_storage.entity.State;
import com.dmos.dmos_manageserver.dmos_storage.service.StateService;
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

    public DMOSResponse queryState(DMOSRequest request){
        Object o = request.getData().get("id");
        if(o == null)
            return DMOSResponse.buildFailsResponse("需要查询列表", null);
        List<Integer> ids = ParseUtil.parse(o, new TypeToken<List<Integer>>(){}.getType());
        HashMap<String, Object> data = new HashMap<>();
        for(int id: ids){
            data.put(String.valueOf(id), State.toDTO(stateService.findById(id)));
        }
        return DMOSResponse.buildSuccessResponse(data);
    }
}
