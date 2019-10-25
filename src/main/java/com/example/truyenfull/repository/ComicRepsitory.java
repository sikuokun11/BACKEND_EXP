package com.example.truyenfull.repository;

import com.example.truyenfull.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicRepsitory extends JpaRepository<Comic,Long> {
}
