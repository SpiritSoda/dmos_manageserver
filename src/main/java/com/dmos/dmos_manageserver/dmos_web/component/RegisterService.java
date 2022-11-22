package com.dmos.dmos_manageserver.dmos_web.component;

import com.dmos.dmos_common.data.ConfigDTO;
import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_common.util.ParseUtil;
import com.dmos.dmos_manageserver.dmos_register.component.DMOSRegisterContext;
import com.dmos.dmos_manageserver.dmos_register.component.tree.TreeNode;
import com.dmos.dmos_manageserver.dmos_register.entity.Node;
import com.dmos.dmos_manageserver.dmos_register.service.NodeService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class RegisterService {
    private final DMOSRegisterContext registerContext;
    private final NodeService nodeService;

    @Autowired
    public RegisterService(DMOSRegisterContext registerContext, NodeService nodeService) {
        this.registerContext = registerContext;
        this.nodeService = nodeService;
    }

    public DMOSResponse queryStructure(){
        HashMap<String, Object> data = new HashMap<>();
        data.put("tree", registerContext.getTree());
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
        return DMOSResponse.buildSuccessResponse(null);
    }
}
