package com.example.truyenfull.repository;

import com.example.truyenfull.model.Chaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterRepository  extends JpaRepository<Chaper,Long> {
    Optional<Chaper> findById(Long id);

    @Query(value = "select * from Chaper c where  c.id= ?1",nativeQuery = true)
    List<Chaper> findByIdNativeQuery(Long id);

    @Query(value = "select  c from Chaper c where  c.id= ?1",nativeQuery = false)
    List<Chaper> findByIdJPQLQuery(Long id);
}
