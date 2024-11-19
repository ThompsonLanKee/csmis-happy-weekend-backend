package com.spring.csmis.service.menu;

import com.spring.csmis.dto.meal.MealDTO;
import com.spring.csmis.dto.menu.MenuWeekDTO;
import com.spring.csmis.entity.CateringCentreEntity;
import com.spring.csmis.entity.MenuEntity;
import com.spring.csmis.entity.MealEntity;
import com.spring.csmis.entity.MenuWeekEntity;
import com.spring.csmis.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuWeekServiceImpl implements MenuWeekService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final MealCategoryRepository mealCategoryRepository;
    private final MealTypeRepository mealTypeRepository;
    private final MealRepository mealRepository;
    private final MenuWeekRepository menuWeekRepository;
    private final CateringCentreRepository cateringCentreRepository;  // Added repository

    @Autowired
    public MenuWeekServiceImpl(MenuRepository menuRepository, ModelMapper modelMapper, MealCategoryRepository mealCategoryRepository, MealTypeRepository mealTypeRepository, MealRepository mealRepository, MenuWeekRepository menuWeekRepository, CateringCentreRepository cateringCentreRepository) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
        this.mealCategoryRepository = mealCategoryRepository;
        this.mealTypeRepository = mealTypeRepository;
        this.mealRepository = mealRepository;
        this.menuWeekRepository = menuWeekRepository;
        this.cateringCentreRepository = cateringCentreRepository;  // Initialize repository
    }

    @Override
    public MenuWeekDTO addMenuWeek(MenuWeekDTO menuWeekDTO) {
        MenuWeekEntity menuWeekEntity = modelMapper.map(menuWeekDTO, MenuWeekEntity.class);

        // Fetch and set menus for this MenuWeek based on Menu IDs in DTO
        List<MenuEntity> menuEntities = menuWeekDTO.getMenus().stream()
                .map(menuDTO -> menuRepository.findById(menuDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Menu not found with id: " + menuDTO.getId())))
                .collect(Collectors.toList());
        menuWeekEntity.setMenus(menuEntities);

        menuWeekEntity.setDeleted(false);  // Ensure it's not marked as deleted

        // Fetch and set CateringCentreEntity based on centreId in DTO
        CateringCentreEntity cateringCentreEntity = cateringCentreRepository.findById(menuWeekDTO.getCentreId())
                .orElseThrow(() -> new RuntimeException("Catering Centre not found with id: " + menuWeekDTO.getCentreId()));
        menuWeekEntity.setCateringCentre(cateringCentreEntity);  // Associate CateringCentreEntity

        MenuWeekEntity savedMenuWeek = menuWeekRepository.save(menuWeekEntity);
        return modelMapper.map(savedMenuWeek, MenuWeekDTO.class);
    }

    @Override
    public MenuWeekDTO updateMenuWeek(Long id, MenuWeekDTO menuWeekDTO) {
        MenuWeekEntity existingMenuWeek = menuWeekRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuWeek not found with id: " + id));

        // Update date range for the week
        existingMenuWeek.setFromDate(menuWeekDTO.getFromDate());
        existingMenuWeek.setToDate(menuWeekDTO.getToDate());

        // Fetch and set updated menus
        List<MenuEntity> updatedMenus = menuWeekDTO.getMenus().stream()
                .map(menuDTO -> menuRepository.findById(menuDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Menu not found with id: " + menuDTO.getId())))
                .collect(Collectors.toList());

        existingMenuWeek.setMenus(updatedMenus);

        // Update the CateringCentreEntity if centreId is provided in the DTO
        CateringCentreEntity cateringCentreEntity = cateringCentreRepository.findById(menuWeekDTO.getCentreId())
                .orElseThrow(() -> new RuntimeException("Catering Centre not found with id: " + menuWeekDTO.getCentreId()));
        existingMenuWeek.setCateringCentre(cateringCentreEntity);

        MenuWeekEntity updatedMenuWeek = menuWeekRepository.save(existingMenuWeek);
        return modelMapper.map(updatedMenuWeek, MenuWeekDTO.class);
    }

    @Override
    public List<MenuWeekDTO> getAllMenusWeek() {
        List<MenuWeekEntity> menuWeeks = menuWeekRepository.findByIsDeletedFalse();
        return menuWeeks.stream()
                .map(menuWeekEntity -> modelMapper.map(menuWeekEntity, MenuWeekDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuEntity> getMenusByMenuWeekFromDateAndToDate(LocalDate fromDate, LocalDate toDate) {
        // Fetch menus directly from the repository using a JOIN query
        return menuWeekRepository.findMenusByMenuWeekDateRange(fromDate, toDate);
    }

    @Override
    public List<MenuWeekDTO> getAllRemovedMenusWeek() {
        List<MenuWeekEntity> removedMenuWeeks = menuWeekRepository.findByIsDeletedTrue();
        return removedMenuWeeks.stream()
                .map(menuWeekEntity -> modelMapper.map(menuWeekEntity, MenuWeekDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MenuWeekDTO getMenuWeekById(Long id) {
        MenuWeekEntity menuWeekEntity = menuWeekRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuWeek not found with id: " + id));
        return modelMapper.map(menuWeekEntity, MenuWeekDTO.class);
    }

    @Override
    public void softDeleteMenuWeek(Long id) {
        MenuWeekEntity menuWeekEntity = menuWeekRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuWeek not found with id: " + id));
        menuWeekEntity.setDeleted(true);
        menuWeekRepository.save(menuWeekEntity); // Apply soft delete
    }

    @Override
    public void hardDeleteMenuWeek(Long id) {
        menuWeekRepository.deleteById(id);
    }

    @Override
    public void restoreMenuWeek(Long id) {
        MenuWeekEntity menuWeekEntity = menuWeekRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuWeek not found with id: " + id));
        menuWeekEntity.setDeleted(false);
        menuWeekRepository.save(menuWeekEntity); // Restore the menu week
    }

    // Utility method to convert MealEntity to MealDTO
    public MealDTO convertToMealDTO(MealEntity mealEntity) {
        MealDTO mealDTO = new MealDTO();
        mealDTO.setId(mealEntity.getId());
        mealDTO.setMealName(mealEntity.getMealName());
        mealDTO.setTypeId(mealEntity.getType().getId());
        mealDTO.setTypeName(mealEntity.getType().getTypeName()); // Assuming MealTypeEntity has a `typeName` field
        mealDTO.setCategoryId(mealEntity.getCategory().getId());
        mealDTO.setCategoryName(mealEntity.getCategory().getCategoryName()); // Assuming MealCategoryEntity has a `categoryName` field
        return mealDTO;
    }

    @Override
    public List<MenuWeekEntity> getMenuPricesForMonth(int month, int year) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return menuWeekRepository.findByDateRange(startDate, endDate);
    }
}
