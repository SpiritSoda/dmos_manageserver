package com.dmos.dmos_manageserver.dmos_register.component;

import com.dmos.dmos_common.data.ServerReportDTO;
import com.dmos.dmos_manageserver.dmos_register.component.tree.TreeNode;
import com.dmos.dmos_manageserver.dmos_register.util.RegisterReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Data
public class DMOSRegisterContext {
    private Map<Integer, NodeRelation> nodes = new ConcurrentHashMap<>();
    public RegisterReport report(ServerReportDTO reportDTO){
        int id = reportDTO.getId();
        HashSet<Integer> child = getChild(id);
        List<Integer> online = new ArrayList<>(), offline = new ArrayList<>();
        for(Integer node: reportDTO.getChild()){
            if(!child.contains(node)){
                online.add(node);
                nodes.put(node, new NodeRelation(node, id));
            }
            else
                child.remove(node);
        }
        for(Integer node: child){
            offline.add(node);
            nodes.remove(node);
        }
        return new RegisterReport(online, offline);
    }
    public HashSet<Integer> getChild(int id){
        HashSet<Integer> child = new HashSet<>();
        for(NodeRelation node: nodes.values()){
            if(node.getParent() == id)
                child.add(node.getId());
        }
        return child;
    }
    public TreeNode getTree(){
        TreeNode root = new TreeNode(0);
        root.setChild(getTreeChild(0));
        return root;
    }
    private List<TreeNode> getTreeChild(int id){
        List<TreeNode> nodes = new ArrayList<>();
        HashSet<Integer> childs = getChild(id);
        for(Integer child: childs){
            TreeNode node = new TreeNode(child);
            node.setChild(getTreeChild(child));
            nodes.add(node);
        }
        return nodes;
    }
}
@Data
@AllArgsConstructor
class NodeRelation {
    private int id;
    private int parent;
    @Override
    public int hashCode(){
        return id;
    }
}

