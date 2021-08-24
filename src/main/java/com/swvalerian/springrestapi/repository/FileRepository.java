package com.swvalerian.springrestapi.repository;

import com.swvalerian.springrestapi.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
