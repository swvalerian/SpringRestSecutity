package com.swvalerian.springrestapi.repository;

import com.swvalerian.springrestapi.model.UserPassw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPasswRepository extends JpaRepository<UserPassw, Long> {
    Optional<UserPassw> findByEmail(String email);
}
