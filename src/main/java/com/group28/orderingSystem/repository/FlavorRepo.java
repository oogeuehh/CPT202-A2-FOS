package com.group28.orderingSystem.repository;

import com.group28.orderingSystem.model.Flavor;
import com.group28.orderingSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  FlavorRepo extends JpaRepository<Flavor, Integer> {
    List<Flavor> findByMenuId(Integer menuId);

}
