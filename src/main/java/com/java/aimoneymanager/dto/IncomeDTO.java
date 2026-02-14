package com.java.aimoneymanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeDTO {

    private Long idl;
    private String name;
    private String description;
    private String icon;
    private BigDecimal amount;
    private Long categoryId;
    private Long profileId;
    private LocalDate incomeDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
