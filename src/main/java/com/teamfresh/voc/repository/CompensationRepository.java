package com.teamfresh.voc.repository;


import com.teamfresh.voc.model.Compensation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompensationRepository extends JpaRepository<Compensation,Long> {
    List<Compensation> findAllByOrderByIdAsc();
}
