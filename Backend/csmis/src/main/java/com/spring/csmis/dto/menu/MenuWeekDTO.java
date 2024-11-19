package com.spring.csmis.dto.menu;

import com.spring.csmis.entity.MenuEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class MenuWeekDTO {
    private Long id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private List<MenuEntity> menus;
    private Long centreId;
    private double price;

}
