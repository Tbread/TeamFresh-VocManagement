package com.teamfresh.voc.repository;

import com.teamfresh.voc.model.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenaltyRepository extends JpaRepository<Penalty,Long> {
}
