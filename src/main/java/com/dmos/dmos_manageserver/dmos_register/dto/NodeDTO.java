package com.dmos.dmos_manageserver.dmos_register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NodeDTO {
    private int id;
    private int type;
    private String name;
    private String token;
    private String ip;
}
