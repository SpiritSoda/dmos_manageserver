package com.dmos.dmos_manageserver.dmos_register.controller;

import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_common.util.ParseUtil;
import com.dmos.dmos_manageserver.dmos_register.config.JwtConfig;
import com.dmos.dmos_common.data.NodeDTO;
import com.dmos.dmos_manageserver.dmos_register.entity.Node;
import com.dmos.dmos_manageserver.dmos_register.service.NodeService;
import com.dmos.dmos_manageserver.dmos_register.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

@Slf4j
@RestController
public class RegisterController {
    private final NodeService nodeService;
    private final JwtConfig jwtConfig;

    @Autowired
    public RegisterController(NodeService nodeService, JwtConfig jwtConfig) {
        this.nodeService = nodeService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/api/register/register")
    public DMOSResponse register(@RequestBody @Valid DMOSRequest dmosRequest){
        NodeDTO nodeDTO = ParseUtil.parse(dmosRequest.getData().get("info"), NodeDTO.class);
//        log.info("Registering: " + nodeDTO.toString());
        if(nodeDTO.getInterval() == 0)
            return DMOSResponse.buildFailsResponse("必须设置报告间隔interval", null);
        Node node = nodeService.register();
        if(nodeDTO.getName() == null)
            nodeDTO.setName("Machine " + nodeDTO.getId());
        if(nodeDTO.getIp() == null)
            nodeDTO.setIp("unset");
        nodeDTO.setId(node.getId());
        String token = JwtUtils.sign(nodeDTO, jwtConfig);
        nodeDTO.setToken(token);
//        log.info("Registered: " + nodeDTO.toString());

        nodeService.update(nodeDTO);
        HashMap<String, Object> data = new HashMap<>();
        data.put("token", nodeDTO.getToken());
        data.put("id", nodeDTO.getId());

        return DMOSResponse.buildSuccessResponse(data);
    }

    @PostMapping("/api/register/token")
    public DMOSResponse token(HttpServletRequest request){
        String token = request.getHeader("token");
        HashMap<String, Object> data = new HashMap<>();
        data.put("token", JwtUtils.sign(token, jwtConfig));
        // id token
        return DMOSResponse.buildSuccessResponse(data);
    }
    @PostMapping("/api/register/verify")
    public DMOSResponse verify(@RequestBody @Valid DMOSRequest dmosRequest){
        int id = JwtUtils.verify((String) dmosRequest.getData().get("token"), jwtConfig);
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        // id
        return DMOSResponse.buildSuccessResponse(data);
    }
}
