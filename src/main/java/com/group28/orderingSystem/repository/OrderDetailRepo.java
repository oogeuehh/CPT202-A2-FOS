package com.group28.orderingSystem.repository;

import com.group28.orderingSystem.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> findByOrderId(int orderId);

}
