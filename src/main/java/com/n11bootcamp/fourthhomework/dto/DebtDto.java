package com.n11bootcamp.fourthhomework.dto;


import com.n11bootcamp.fourthhomework.entity.enumeration.DebtType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebtDto {
    private Long id;

    private BigDecimal mainDebtAmount;

    private BigDecimal remainingDebt;

    @Enumerated(EnumType.STRING)
    private DebtType debtType;

    private Long userId;

    private LocalDate expiryDate;

    private BigDecimal lateFee;

    private Long connectedDebt;
}
