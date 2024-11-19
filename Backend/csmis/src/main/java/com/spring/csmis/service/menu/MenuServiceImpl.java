package com.spring.csmis.service.menu;

import com.spring.csmis.dto.meal.MealDTO;
import com.spring.csmis.dto.menu.MenuDTO;
import com.spring.csmis.entity.MenuEntity;
import com.spring.csmis.entity.MealEntity;
import com.spring.csmis.repository.MealRepository;
import com.spring.csmis.repository.MenuRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final MealRepository mealRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository, ModelMapper modelMapper, MealRepository mealRepository) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
        this.mealRepository = mealRepository;
    }

    @Override
    public MenuDTO addMenu(MenuDTO menuDTO) {
        MenuEntity menuEntity = modelMapper.map(menuDTO, MenuEntity.class);

        // Fetch and set meals for this menu based on meal IDs in DTO
        List<MealEntity> mealEntities = menuDTO.getMeals().stream()
                .map(mealDTO -> mealRepository.findById(mealDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Meal not found with id: " + mealDTO.getId())))
                .collect(Collectors.toList());

        // Set meals and handle optional meal if present
        menuEntity.setMeals(mealEntities);
        if (menuDTO.getOptionalMealId() != null) {
            MealEntity optionalMeal = mealRepository.findById(menuDTO.getOptionalMealId())
                    .orElseThrow(() -> new RuntimeException("Optional meal not found with id: " + menuDTO.getOptionalMealId()));
            menuEntity.setOptional(optionalMeal);
        }

        menuEntity.setDeleted(false);
        //menuEntity.getPrice();
        menuEntity.getNo_of_pax();
        MenuEntity savedMenu = menuRepository.save(menuEntity);

        MenuDTO savedMenuDTO = modelMapper.map(savedMenu, MenuDTO.class);
        if (savedMenu.getOptional() != null) {
            savedMenuDTO.setOptionalMealName(savedMenu.getOptional().getMealName());
        }

        return savedMenuDTO;
    }

    @Override
    public MenuDTO updateMenu(Long id, MenuDTO menuDTO) {
        MenuEntity existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));

        // Update existing menu details
        existingMenu.setDayName(menuDTO.getDayName());
        existingMenu.setDate(menuDTO.getDate());

        // Fetch and update meals
        List<MealEntity> mealEntities = menuDTO.getMeals().stream()
                .map(mealDTO -> mealRepository.findById(mealDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Meal not found with id: " + mealDTO.getId())))
                .collect(Collectors.toList());
        existingMenu.setMeals(mealEntities);

        // Update optional meal if applicable
        if (menuDTO.getOptionalMealId() != null) {
            MealEntity optionalMeal = mealRepository.findById(menuDTO.getOptionalMealId())
                    .orElseThrow(() -> new RuntimeException("Optional meal not found with id: " + menuDTO.getOptionalMealId()));
            existingMenu.setOptional(optionalMeal);
        }

        MenuEntity updatedMenu = menuRepository.save(existingMenu);
        return modelMapper.map(updatedMenu, MenuDTO.class);
    }

    @Override
    public List<MenuDTO> getAllMenus() {
        return menuRepository.findByIsDeletedFalse().stream()
                .map(menuEntity -> modelMapper.map(menuEntity, MenuDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuDTO> getAllRemovedMenus() {
        return menuRepository.findByIsDeletedTrue().stream()
                .map(menuEntity -> modelMapper.map(menuEntity, MenuDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MenuDTO getMenuById(Long id) {
        MenuEntity menuEntity = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));
        return modelMapper.map(menuEntity, MenuDTO.class);
    }

    @Override
    public void softDeleteMenu(Long id) {
        MenuEntity menuEntity = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));
        menuEntity.setDeleted(true);
        menuRepository.save(menuEntity);
    }

    @Override
    public void hardDeleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public void restoreMenu(Long id) {
        MenuEntity menuEntity = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));
        menuEntity.setDeleted(false);
        menuRepository.save(menuEntity);
    }

    public MealDTO convertToMealDTO(MealEntity mealEntity) {
        MealDTO mealDTO = new MealDTO();
        mealDTO.setId(mealEntity.getId());
        mealDTO.setMealName(mealEntity.getMealName());
        mealDTO.setTypeId(mealEntity.getType().getId());
        mealDTO.setTypeName(mealEntity.getType().getTypeName());
        mealDTO.setCategoryId(mealEntity.getCategory().getId());
        mealDTO.setCategoryName(mealEntity.getCategory().getCategoryName());
        return mealDTO;
    }
}
