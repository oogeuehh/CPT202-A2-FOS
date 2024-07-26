package com.group28.orderingSystem.repository;

import com.group28.orderingSystem.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  userRepo extends JpaRepository<User, Integer> {
//public interface  userRepo extends PagingAndSortingRepository<User, Integer> {
    User findByUsername(String username);
//    User findById(Integer id);

    User findByUsernameAndPassword(String username, String password);

    User findByUsernameAndEmailAndPhone(String username, String email, String phone);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);


//    List<User> findByRole_Role(String roleName);
        List<User> findByRole_Id(Integer roleId);
    // 添加按照lastLogin时间排序查找所有用户的方法
    List<User> findAllByOrderByLastLoginDesc();
    List<User> findByRole_RoleAndAccountStatus_Status(String roleName, String status);
//    @Transactional
//    @Modifying
//    @Query("UPDATE User u SET u.username = :username, u.email = :email, u.phone = :phone WHERE u.id = :userId")
//    void updateUserProfile(User user);
//    void updateUserProfile(Integer userId, String username, String email, String phone);
}
