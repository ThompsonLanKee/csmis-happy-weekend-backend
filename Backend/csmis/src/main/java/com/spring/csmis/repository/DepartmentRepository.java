package com.spring.csmis.repository;

import com.spring.csmis.entity.DepartmentEntity;
import com.spring.csmis.entity.DivisionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
    Optional<DepartmentEntity> findByName(String name);

    @Query("select department from DepartmentEntity department where department.isDeleted<>true")
    public List<DepartmentEntity> showAllDepartments();

    @Modifying
    @Transactional
    @Query("update DepartmentEntity department set department.isDeleted=true where department.id=:id")
    public void deleteDepartment(Integer id);

    List<DepartmentEntity> findByDivisionId(int divisionId);

    List<DepartmentEntity> findByNameAndDivision(String name, DivisionEntity division);


    @Query("SELECT d FROM DepartmentEntity d WHERE d.name = :name AND d.division.name = :division")
    Optional<DepartmentEntity> findByNameAndDivision(@Param("name") String name, @Param("division") String division);

//    Optional<Department> findDepartByDivision(String name, Division division);


//    List<Department> findByNameAndDivision(String name, Division division);


//    List<Department> findAllByName(String departmentName);
}
