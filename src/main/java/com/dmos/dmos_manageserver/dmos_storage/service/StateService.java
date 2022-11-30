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
        State state = State.fromDTO(reportDTO);
        stateRepository.save(state);
    }
    public State findById(int id){
        return stateRepository.findStateById(id);
    }
}
