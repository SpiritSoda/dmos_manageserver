package com.dmos.dmos_manageserver.dmos_storage.entity;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
@Data
//@Table(name = "info")
public class Info {
    @Id
//    @NonNull
    @Column(name = "client_id")
    private int clientId;
    // 内存
    private double mem;
    // CPU
    private double cpu;
    // 硬盘
    private double disk;
    // 温度
    private double temperature;
    // 操作系统
    private String OS;
}
