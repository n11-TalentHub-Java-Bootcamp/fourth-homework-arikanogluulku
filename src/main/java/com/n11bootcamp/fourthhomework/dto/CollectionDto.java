package com.n11bootcamp.fourthhomework.dto;

import com.n11bootcamp.fourthhomework.entity.enumeration.DebtType;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CollectionDto {

    private LocalDate dateOfRegistration;

    private Long debtId;

    private Long connectedDebtId;

    private BigDecimal collectionAmount;

    private DebtType debtType;
}