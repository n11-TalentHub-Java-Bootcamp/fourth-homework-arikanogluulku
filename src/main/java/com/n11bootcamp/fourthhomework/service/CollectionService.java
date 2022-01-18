package com.n11bootcamp.fourthhomework.service;

import com.n11bootcamp.fourthhomework.dto.CollectionDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public interface CollectionService {
    Object createCollection(BigDecimal amount , Long debtId);

    List<CollectionDto> getCollectionsInDateRange(LocalDate startDate, LocalDate endDate);

    List<CollectionDto>  getUsersCollections(Long userId);

    BigDecimal getUserLateFeeTotal(Long userId);
}
