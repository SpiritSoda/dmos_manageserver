package com.dmos.dmos_manageserver.dmos_register.service;

import com.dmos.dmos_common.data.NodeDTO;
import com.dmos.dmos_manageserver.dmos_register.entity.Node;
import com.dmos.dmos_manageserver.dmos_register.repo.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodeService {
    private final NodeRepository nodeRepository;

    @Autowired
    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public Node register(){
        Node node = new Node();
        return nodeRepository.save(node);
    }
    public Node update(NodeDTO nodeDTO){
        Node node = new Node();
        node.setId(nodeDTO.getId());
        node.setName(nodeDTO.getName());
        node.setType(nodeDTO.getType());
        node.setToken(nodeDTO.getToken());
        node.setIp(nodeDTO.getIp());
        node.setInterval(nodeDTO.getInterval());
        return nodeRepository.save(node);
    }
    public Node findById(int id){
        return nodeRepository.findNodeById(id);
    }
}
