package com.spring.csmis.dto;


import com.spring.csmis.entity.EmployeeEntity;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {
    public static EmployeeDTO toDTO(EmployeeEntity employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setStaff_ID(employee.getStaffID());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setStatus(employee.getStatus());
        dto.setDivision(employee.getDivision().getName());
        dto.setDepartment(employee.getDepartment().getName());
        dto.setTeam(employee.getTeam().getName());
        dto.setJoined_at(employee.getJoined_at());
        dto.setDeleted(employee.isDeleted());

        return dto;
    }

    public static List<EmployeeDTO> toDTOList(List<EmployeeEntity> employees) {
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }
}
