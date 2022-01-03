package com.teamfresh.voc.repository;

import com.teamfresh.voc.model.VOC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VOCRepository extends JpaRepository<VOC,Long> {
    List<VOC> findAllByOrderByIdAsc();
    VOC findByCompensationId(Long id);
}
