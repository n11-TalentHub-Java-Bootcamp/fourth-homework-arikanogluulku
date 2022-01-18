package com.n11bootcamp.fourthhomework.service.impl;

import com.n11bootcamp.fourthhomework.converter.CollectionMapper;
import com.n11bootcamp.fourthhomework.converter.DebtMapper;
import com.n11bootcamp.fourthhomework.dao.CollectionRepository;
import com.n11bootcamp.fourthhomework.dto.CollectionDto;
import com.n11bootcamp.fourthhomework.dto.DebtCreatDto;
import com.n11bootcamp.fourthhomework.dto.DebtDto;
import com.n11bootcamp.fourthhomework.entity.Collection;
import com.n11bootcamp.fourthhomework.entity.Debt;
import com.n11bootcamp.fourthhomework.entity.enumeration.DebtType;
import com.n11bootcamp.fourthhomework.service.CollectionService;
import com.n11bootcamp.fourthhomework.service.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final DebtService debtService;

    @Override
    public Object createCollection(BigDecimal amount , Long debtId) {

        DebtDto debtDto = debtService.getOneDebt(debtId);
        BigDecimal amountDue = debtDto.getRemainingDebt().add(debtDto.getLateFee());

        if(amountDue.floatValue() == amount.floatValue()){
            CollectionDto collectionDto = CollectionDto.builder()
                    .debtId(debtDto.getId())
                    .dateOfRegistration(LocalDate.now())
                    .collectionAmount(debtDto.getRemainingDebt())
                    .debtType(DebtType.NORMAL)
                    .build();
            Collection collection = CollectionMapper.INSTANCE.convertToCollection(collectionDto);
            debtDto.setRemainingDebt(BigDecimal.ZERO);
            debtService.updateOneDebt(debtDto.getId() , debtDto);
            collectionRepository.save(collection);

            if(debtDto.getLateFee() != BigDecimal.ZERO){
                DebtCreatDto debtCreatDto = DebtCreatDto.builder()
                        .mainDebtAmount(debtDto.getLateFee())
                        .remainingDebt(BigDecimal.ZERO)
                        .userId(debtDto.getUserId())
                        .expiryDate(LocalDate.now())
                        .connectedDebt(debtDto.getId())
                        .build();
                DebtDto debt = debtService.createDebt(debtCreatDto, DebtType.LATE_FEE);

                CollectionDto collectionDtoLateFee = CollectionDto.builder()
                        .debtId(debt.getId())
                        .dateOfRegistration(LocalDate.now())
                        .collectionAmount(debt.getMainDebtAmount())
                        .connectedDebtId(debt.getConnectedDebt())
                        .debtType(DebtType.LATE_FEE)
                        .build();
                Collection collectionLateFee = CollectionMapper.INSTANCE.convertToCollection(collectionDtoLateFee);
                collectionRepository.save(collectionLateFee);
            }
            return new ResponseEntity("Collection has been made" , HttpStatus.CREATED);
        }

        return new ResponseEntity("Collection failed" ,HttpStatus.BAD_REQUEST );
    }

    @Override
    public List<CollectionDto> getCollectionsInDateRange(LocalDate startDate, LocalDate endDate) {
        List<Collection> collections = collectionRepository.findAllByDateOfRegistrationBetween(startDate,endDate);
        List<CollectionDto> collectionDtoList = CollectionMapper.INSTANCE.convertAllToCollectionDto(collections);
        return collectionDtoList;
    }

    @Override
    public List<CollectionDto> getUsersCollections(Long userId) {
        List<Collection> collections = collectionRepository.findByUserCollections(userId);
        List<CollectionDto> collectionDtoList=CollectionMapper.INSTANCE.convertAllToCollectionDto(collections);
        return collectionDtoList;
    }

    @Override
    public BigDecimal getUserLateFeeTotal(Long userId) {
        List<Collection> collections = collectionRepository.findByUserDebtTypeLateFee(userId);
        BigDecimal sum = BigDecimal.ZERO;
        for(Collection collection : collections){
            sum = sum.add(collection.getCollectionAmount());
        }

        return sum;
    }
}
