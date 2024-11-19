package com.spring.csmis.dto;

import lombok.Data;


import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

@Data
public class LunchRegistrationDTO {
    private Long id;
    private Long userId;  // Reference to the User ID
    private Long monthId; // Reference to the Month ID
    private Map<Integer, Boolean> dailyLunchStatus; // Day-to-lunch status map
    private Set<Long> avoidMeatIds; // IDs of meats to avoid
    private LocalTime modificationDeadline;


}
