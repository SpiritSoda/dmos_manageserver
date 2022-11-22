package com.dmos.dmos_manageserver.dmos_register.controller;

import com.dmos.dmos_common.data.DMOSRequest;
import com.dmos.dmos_common.data.DMOSResponse;
import com.dmos.dmos_manageserver.dmos_register.config.JwtConfig;
import com.dmos.dmos_manageserver.dmos_register.dto.NodeDTO;
import com.dmos.dmos_manageserver.dmos_register.entity.Node;
import com.dmos.dmos_manageserver.dmos_register.service.NodeService;
import com.dmos.dmos_manageserver.dmos_register.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

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
        Node node = nodeService.register();
        String name = (String) dmosRequest.getData().get("name");
        String ip = (String) dmosRequest.getData().get("ip");
        int type = (Integer) dmosRequest.getData().get("type");
        NodeDTO nodeDTO = new NodeDTO(node.getId(), node.getType(), node.getName(), "", node.getIp());
        String token = JwtUtils.sign(nodeDTO, jwtConfig);
        nodeDTO.setToken(token);

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
