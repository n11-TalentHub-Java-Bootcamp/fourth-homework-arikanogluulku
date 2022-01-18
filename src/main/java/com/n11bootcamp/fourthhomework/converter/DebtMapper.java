package com.n11bootcamp.fourthhomework.converter;

import com.n11bootcamp.fourthhomework.dto.DebtCreatDto;
import com.n11bootcamp.fourthhomework.dto.DebtDto;
import com.n11bootcamp.fourthhomework.entity.Debt;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE )

public interface DebtMapper {

    DebtMapper INSTANCE = Mappers.getMapper(DebtMapper.class);

    //@Mapping(target = "user.id", source = "userId")
    Debt convertToDebt(DebtDto debtDto);

    //@Mapping(target = "user.id", source = "userId")
    Debt convertToDebt(DebtCreatDto debtCreatDto);


    //@Mapping(target = "userId", source = "user.id")
    DebtDto convertToDebtDto(Debt debt);

    // @Mapping(target = "userId", source = "user.id")
    List<DebtDto> convertAllToDebtDto(List<Debt> debts);



}
