package com.group28.orderingSystem.repository;
import com.group28.orderingSystem.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface TypeRepo extends JpaRepository<Type, Integer> {
    List<Type> findAll();
    List<Type> findByTypeNameContaining(String name);
    }
