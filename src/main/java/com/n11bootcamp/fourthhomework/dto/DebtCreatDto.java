package com.n11bootcamp.fourthhomework.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class DebtCreatDto {

    private BigDecimal mainDebtAmount;

    private BigDecimal remainingDebt;

    private Long userId;

    private LocalDate expiryDate;

    private Long connectedDebt;



}
