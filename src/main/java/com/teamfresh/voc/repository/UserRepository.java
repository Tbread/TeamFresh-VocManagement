package com.teamfresh.voc.repository;

import com.teamfresh.voc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
}
