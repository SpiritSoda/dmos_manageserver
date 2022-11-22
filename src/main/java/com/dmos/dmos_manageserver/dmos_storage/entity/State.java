package com.dmos.dmos_manageserver.dmos_storage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
@Data
@Entity
@Table(name = "state")
@AllArgsConstructor
@NoArgsConstructor
public class State {
    @Id
    @Column(name = "id")
    private int id;
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
