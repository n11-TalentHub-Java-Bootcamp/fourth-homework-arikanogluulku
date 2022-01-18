package com.n11bootcamp.fourthhomework.service;

import com.n11bootcamp.fourthhomework.dto.DebtCreatDto;
import com.n11bootcamp.fourthhomework.dto.DebtDto;
import com.n11bootcamp.fourthhomework.entity.Debt;
import com.n11bootcamp.fourthhomework.entity.enumeration.DebtType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public interface DebtService {

    DebtDto createDebt(DebtCreatDto newDebt, DebtType debtType);

    List<DebtDto> getDebtsInDateRange(LocalDate startDate, LocalDate endDate);

    DebtDto getOneDebt(Long debtId);

    DebtDto updateOneDebt(Long debtId , DebtDto debtDto);

    List<DebtDto> getUserDebts(Long userId);

    List<DebtDto> getOverdueUserDebts(Long userId);

    BigDecimal getUserTotalDebt(Long userId);

    BigDecimal getUserOverTotalDebt(Long userId);

    BigDecimal getUserTotalLateInterest(Long userId);
}
