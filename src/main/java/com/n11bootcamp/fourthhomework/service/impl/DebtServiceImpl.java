package com.n11bootcamp.fourthhomework.service.impl;

import com.n11bootcamp.fourthhomework.converter.DebtMapper;
import com.n11bootcamp.fourthhomework.dao.DebtRepository;
import com.n11bootcamp.fourthhomework.dto.DebtCreatDto;
import com.n11bootcamp.fourthhomework.dto.DebtDto;
import com.n11bootcamp.fourthhomework.dto.UserDto;
import com.n11bootcamp.fourthhomework.entity.Debt;
import com.n11bootcamp.fourthhomework.entity.enumeration.DebtType;
import com.n11bootcamp.fourthhomework.exception.NotFoundException;
import com.n11bootcamp.fourthhomework.service.DebtService;
import com.n11bootcamp.fourthhomework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.n11bootcamp.fourthhomework.util.Constants.*;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
@Transactional
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;
    private final UserService userService;


    @Override
    public DebtDto createDebt(DebtCreatDto newDebt, DebtType debtType) {

        Debt debt = DebtMapper.INSTANCE.convertToDebt(newDebt);
        debt.setDebtType(debtType);
        debtRepository.save(debt);
        return DebtMapper.INSTANCE.convertToDebtDto(debt);
    }


    @Override
    public List<DebtDto> getDebtsInDateRange(LocalDate startDate, LocalDate endDate) {

        List<Debt> debts = debtRepository.findAllByExpiryDateBetween(startDate, endDate);

        List<DebtDto> debtDtos = DebtMapper.INSTANCE.convertAllToDebtDto(debts);

        for (DebtDto debtDto : debtDtos) {
            debtDto.setLateFee(calculateLateInterest(debtDto.getExpiryDate(), debtDto.getMainDebtAmount()));
        }
        return debtDtos;
    }

    @Override
    public DebtDto getOneDebt(Long debtId) {

        Optional<Debt> debt = debtRepository.findById(debtId);

        if (debt.isPresent()) {
            DebtDto debtDto = DebtMapper.INSTANCE.convertToDebtDto(debt.get());
            debtDto.setLateFee(calculateLateInterest(debtDto.getExpiryDate(), debtDto.getMainDebtAmount()));
            return debtDto;
        } else {
            throw new NotFoundException(" Debt Id :" + debtId + " Debt Not Found ! ");
        }
    }

    @Override
    public DebtDto updateOneDebt(Long debtId, DebtDto debtDto) {
        DebtDto debt = getOneDebt(debtDto.getId());

        Debt updatedDebt = DebtMapper.INSTANCE.convertToDebt(debtDto);

        debtRepository.save(updatedDebt);

        return DebtMapper.INSTANCE.convertToDebtDto(updatedDebt);
    }

    @Override
    public List<DebtDto> getUserDebts(Long userId) {

        List<Debt> debts = debtRepository.findByUserId(userId);

        List<DebtDto> debtDtos = DebtMapper.INSTANCE.convertAllToDebtDto(debts);

        for (DebtDto debtDto : debtDtos) {
            debtDto.setLateFee(calculateLateInterest(debtDto.getExpiryDate(), debtDto.getMainDebtAmount()));
        }
        return debtDtos;
    }

    @Override
    public List<DebtDto> getOverdueUserDebts(Long userId) {
        UserDto user = userService.getOneUser(userId);

        List<Debt> debts = debtRepository.findAllOverdueUserDebts(userId);
        List<DebtDto> debtDtos = DebtMapper.INSTANCE.convertAllToDebtDto(debts);

        for (DebtDto debtDto : debtDtos) {
            debtDto.setLateFee(calculateLateInterest(debtDto.getExpiryDate(), debtDto.getMainDebtAmount()));
        }

        return debtDtos;

    }

    @Override
    public BigDecimal getUserTotalDebt(Long userId) {

        UserDto user = userService.getOneUser(userId);

        List<DebtDto> debts = getUserDebts(userId);

        BigDecimal sum = BigDecimal.ZERO;
        for (DebtDto debtDto : debts) {
            sum = sum.add(debtDto.getRemainingDebt());
            sum = sum.add(debtDto.getLateFee());
        }

        return sum;
    }

    @Override
    public BigDecimal getUserOverTotalDebt(Long userId) {

        UserDto user = userService.getOneUser(userId);

        List<DebtDto> debts = getOverdueUserDebts(userId);

        BigDecimal sum = BigDecimal.ZERO;
        for (DebtDto debt : debts) {
            sum = sum.add(debt.getMainDebtAmount());
            sum = sum.add(debt.getLateFee());
        }

        return sum;

    }

    @Override
    public BigDecimal getUserTotalLateInterest(Long userId) {
        List<DebtDto> userDebts = getUserDebts(userId);

        BigDecimal sum = BigDecimal.ZERO;
        for (DebtDto debtDto : userDebts) {
            sum = sum.add(debtDto.getLateFee());
        }
        return sum;
    }


    private BigDecimal calculateLateInterest(LocalDate date, BigDecimal amount) {

        long dayOfLateInterest = DAYS.between(date, LocalDate.now());

        BigDecimal interestAmount = BigDecimal.ZERO;
        if (dayOfLateInterest > 0) {

            if (DAYS.between(LIMIT_DATE, date) < 0) {
                interestAmount = BigDecimal.valueOf(dayOfLateInterest).multiply(percentage(amount, MIN_RATE));

            } else {
                interestAmount = BigDecimal.valueOf(dayOfLateInterest).multiply(percentage(amount, MAX_RATE));
            }

            if (interestAmount.doubleValue() < 1.0) {
                interestAmount = BigDecimal.ONE;
            }
        }
        return interestAmount;
    }
}
