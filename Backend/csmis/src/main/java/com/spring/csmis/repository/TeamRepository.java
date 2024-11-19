package com.spring.csmis.repository;


import com.spring.csmis.entity.DepartmentEntity;
import com.spring.csmis.entity.TeamEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {
    Optional<TeamEntity> findByName(String name);

    @Query("select team from TeamEntity team where team.isDeleted<>true")
    public List<TeamEntity> showAllTeams();

    @Modifying
    @Transactional
    @Query("update TeamEntity team set team.isDeleted=true where team.id=:id")
    public void deleteTeam(Integer id);

    Optional<TeamEntity> findByNameAndDepartment(String name, DepartmentEntity department);

    List<TeamEntity> findAllByNameAndDepartment(String name, DepartmentEntity department);

    List<TeamEntity> findByDepartmentId(int departmentId);


    @Query("SELECT t FROM TeamEntity t WHERE t.name = :name AND t.department.name = :department AND t.department.division.name = :division")
    Optional<TeamEntity> findByNameAndDepartment(@Param("department") String department, @Param("division") String division, @Param("name") String name);

//    List<Team> findByNameAndDepartmentAndDivision(String name, Department department, Division division);

//    Optional<Team> findByNameAndDepartmentAndDivision(String name, Department department, Division division);
}
