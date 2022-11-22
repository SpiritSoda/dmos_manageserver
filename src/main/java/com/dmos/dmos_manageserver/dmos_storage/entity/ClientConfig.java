package com.dmos.dmos_manageserver.dmos_storage.entity;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
@Data
//@Table(name = "client_config")
public class ClientConfig {
    @Id
//    @NonNull
    @Column(name = "client_id")
    private int clientId;

    private int interval;
    private String ip;
}
