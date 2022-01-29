package com.teamfresh.voc.repository;

import com.teamfresh.voc.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
