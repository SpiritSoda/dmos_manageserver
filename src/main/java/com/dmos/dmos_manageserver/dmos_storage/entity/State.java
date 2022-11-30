package com.dmos.dmos_manageserver.dmos_storage.entity;

import com.dmos.dmos_common.data.ClientReportDTO;
import com.dmos.dmos_common.data.state.CPU;
import com.dmos.dmos_common.data.state.Ram;
import com.dmos.dmos_common.data.state.Storage;
import com.dmos.dmos_common.data.state.Swap;
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
    private double ram_percent;
    private double ram_used;
    private double ram_total;
    // CPU
    private double cpu_percent;
    private double cpu_used;
    private double cpu_total;
    // 硬盘
    private double storage_percent;
    private double storage_used;
    private double storage_total;
    // 交换空间
    private double swap_percent;
    private double swap_used;
    private double swap_total;

    private long timestamp;

    public static ClientReportDTO toDTO(State state){
        if(state == null)
            return new ClientReportDTO();
        ClientReportDTO reportDTO = new ClientReportDTO();
        reportDTO.setId(state.getId());
        reportDTO.setRam(new Ram(state.ram_percent, state.ram_used, state.ram_total));
        reportDTO.setCpu(new CPU(state.cpu_percent, state.cpu_used, state.cpu_total));
        reportDTO.setStorage(new Storage(state.storage_percent, state.storage_used, state.storage_total));
        reportDTO.setSwap(new Swap(state.swap_percent, state.swap_used, state.swap_total));
        reportDTO.setTimestamp(state.timestamp);
        return reportDTO;
    }
    public static State fromDTO(ClientReportDTO reportDTO){
        return new State(
                reportDTO.getId(),
                reportDTO.getRam().getPercent(),
                reportDTO.getRam().getUsed(),
                reportDTO.getRam().getTotal(),
                reportDTO.getCpu().getPercent(),
                reportDTO.getCpu().getUsed(),
                reportDTO.getCpu().getTotal(),
                reportDTO.getStorage().getPercent(),
                reportDTO.getStorage().getUsed(),
                reportDTO.getStorage().getTotal(),
                reportDTO.getSwap().getPercent(),
                reportDTO.getSwap().getUsed(),
                reportDTO.getSwap().getTotal(),
                reportDTO.getTimestamp()
        );
    }
}
