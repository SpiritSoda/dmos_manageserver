package com.dmos.dmos_manageserver.dmos_storage.service;

import com.dmos.dmos_common.data.ClientReportDTO;
import com.dmos.dmos_manageserver.dmos_storage.entity.State;
import com.dmos.dmos_manageserver.dmos_storage.repo.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {
    private final StateRepository stateRepository;

    @Autowired
    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public void update(ClientReportDTO reportDTO){
        State state = new State(
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
                reportDTO.getTimestamp()
        );
        stateRepository.save(state);
    }
    public State findById(int id){
        return stateRepository.findStateById(id);
    }
}
