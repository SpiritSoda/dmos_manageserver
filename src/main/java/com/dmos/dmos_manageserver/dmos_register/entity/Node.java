package com.dmos.dmos_manageserver.dmos_register.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "node")
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private int type;
    private String token;
    private String name;

    @Column(name = "timer")
    private int interval;
    private String ip;
}
