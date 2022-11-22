package com.dmos.dmos_manageserver.dmos_register.component.tree;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class TreeNode {
    private Integer id;
    private List<TreeNode> child = new ArrayList<>();
    public TreeNode(int id){
        this.id = id;
    }
}
