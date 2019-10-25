package com.example.truyenfull.repository;

import com.example.truyenfull.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByName(String name);

    @Query(value = "select  * from Category c where  c.name= ?1",nativeQuery = true)
    List<Category> findByNameNativeQuery(String name);

    @Query(value = "select  c from Category c where  c.name= ?1",nativeQuery = false)
    List<Category> findByNameJPQLQuery(String name);
}
