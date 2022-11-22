package com.dmos.dmos_manageserver.dmos_register.util;

import lombok.Data;

import java.util.List;

@Data
public class RegisterReport {
    private List<Integer> online;
    private List<Integer> offline;
    public RegisterReport(List<Integer> online, List<Integer> offline){
        this.offline = offline;
        this.online = online;
    }
}
