package com.dmos.dmos_manageserver.dmos_storage.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
@Data
public class Server {
    @Id
    private int serverId;
    private String ip;
}
