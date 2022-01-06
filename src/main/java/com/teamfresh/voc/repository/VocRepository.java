package com.teamfresh.voc.repository;

import com.teamfresh.voc.model.Voc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VocRepository extends JpaRepository<Voc,Long> {
    List<Voc> findAll();
}
