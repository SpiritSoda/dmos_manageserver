package com.dmos.dmos_manageserver.dmos_web.component;

import com.dmos.dmos_common.data.ConfigDTO;
import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_common.util.ParseUtil;
import com.dmos.dmos_manageserver.dmos_register.entity.Node;
import com.dmos.dmos_manageserver.dmos_register.service.NodeService;
import com.dmos.dmos_server.DMOSServerContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class RegisterService {
    private final DMOSServerContext serverContext;
    private final NodeService nodeService;

    @Autowired
    public RegisterService(DMOSServerContext serverContext, NodeService nodeService) {
        this.serverContext = serverContext;
        this.nodeService = nodeService;
    }

    public DMOSResponse queryStructure(){
        HashMap<String, Object> data = new HashMap<>();
        data.put("tree", serverContext.getTree());
        return DMOSResponse.buildSuccessResponse(data);
    }
    public DMOSResponse queryInfo(DMOSRequest request){
        List<Integer> ids = ParseUtil.parse(request.getData().get("id"), new TypeToken<List<Integer>>(){}.getType());
        log.info("Querying" + ids.toString());
        HashMap<String, Object> data = new HashMap<>();
        for(Integer id: ids){
            Node node = nodeService.findById(id);
            if(node != null)
                node.setToken("");
            data.put(String.valueOf(id), node);
        }
        return DMOSResponse.buildSuccessResponse(data);
    }
    public DMOSResponse config(DMOSRequest request){
        Gson gson = new Gson();
        ConfigDTO configDTO = ParseUtil.parse(request.getData().get("config"), ConfigDTO.class);
        log.info("Config " + configDTO.toString());
        // ====================== setup config ======================= //
        if(configDTO.getId() == 0)
            return DMOSResponse.buildFailsResponse("必须指定目的地", null);
        nodeService.config(configDTO);
        int child = serverContext.findRoute(configDTO.getId());
        serverContext.sendTo(child, configDTO);
        return DMOSResponse.buildSuccessResponse(null);
    }
}
