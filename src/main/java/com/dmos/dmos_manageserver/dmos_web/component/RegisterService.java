package com.dmos.dmos_manageserver.dmos_web.component;

import com.dmos.dmos_common.data.ConfigDTO;
import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_common.message.Message;
import com.dmos.dmos_common.message.MessageType;
import com.dmos.dmos_common.util.ParseUtil;
import com.dmos.dmos_manageserver.dmos_register.entity.Node;
import com.dmos.dmos_manageserver.dmos_register.service.NodeService;
import com.dmos.dmos_manageserver.dmos_web.dto.QueryDTO;
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
    public DMOSResponse queryInfo(QueryDTO queryDTO){
        List<Integer> ids = queryDTO.getId();
//        log.info("Querying" + ids.toString());
        HashMap<String, Object> data = new HashMap<>();
        for(Integer id: ids){
            Node node = nodeService.findById(id);
            if(node != null)
                node.setToken("");
            data.put(String.valueOf(id), node);
        }
        return DMOSResponse.buildSuccessResponse(data);
    }
    public DMOSResponse config(ConfigDTO configDTO){
//        log.info("Config " + configDTO.toString());
        // ====================== setup config ======================= //
        if(configDTO.getId() == 0)
            return DMOSResponse.buildFailsResponse("必须指定目的地", null);
        nodeService.config(configDTO);
        Message message = new Message();
        message.setType(MessageType.CONFIG);
        message.setData(ParseUtil.encode(configDTO, false));
        serverContext.sendToWithoutFlush(configDTO.getId(), message);
        return DMOSResponse.buildSuccessResponse(null);
    }
}
