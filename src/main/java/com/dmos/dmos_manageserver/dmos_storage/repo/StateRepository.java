package com.dmos.dmos_manageserver.dmos_storage.repo;

import com.dmos.dmos_manageserver.dmos_storage.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Integer> {
    State findInfoById(int id);
}
