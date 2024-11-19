package com.spring.csmis.repository;


import com.spring.csmis.entity.UserEntity;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmployeeStaffID(String staffID);

    Optional<UserEntity> findByIdentityNo(String identityNo);

    Optional<UserEntity> findById(Long id);

    @Query("SELECT u FROM UserEntity u WHERE u.deleted = false")
    List<UserEntity> findAllActiveUsers();

//
//
//    UserEntity findByEmployee_IdentityNo(String identityNo);
//
//    Optional<UserEntity> findByEmail(String email);
//
//    //--------------SELECT STATEMENT BEGIN---------------------
//    @Query("SELECT u FROM UserEntity u WHERE u.enabled = true")
//    List<UserEntity> showAllUsers();
//
//    @Query("SELECT u FROM UserEntity u WHERE u.enabled = false")
//    List<UserEntity> showAllRemovedUsers();
//
//
//    @Query("SELECT u FROM UserEntity u WHERE u.employee.identityNo = :identityNo")
//    Optional<UserEntity> findByIdentityNo(@Param("identityNo") String identityNo);
//
//    //
////    Optional<UserEntity> findByIdentityNo(String identityNo);
////    //    //--------------SELECT STATEMENT END---------------------
////
////    //--------------SOFT DELETE(Remove) STATEMENT BEGIN---------------------
//    @Modifying
//    @Transactional
//    @Query("UPDATE UserEntity u SET u.enabled = false WHERE u.id = ?1")
//    void softDeleteUserById(Integer id);
//
//    //    //--------------DELETE STATEMENT BEGIN---------------------
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM UserEntity u WHERE u.id = ?1")
//    void deleteUserById(Integer id);
//
//    //    //--------------Restore Soft Delete------------------------
////
//    @Modifying
//    @Transactional
//    @Query("UPDATE UserEntity u SET u.enabled = true WHERE u.id = ?1")
//    void restoreUserById(Integer id);
//
//
//    @Query("SELECT u FROM UserEntity u WHERE u.username = ?1")
//    UserEntity findByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.employee.email = :email")
    public Optional<UserEntity> findByEmail(String email);

}