package com.n11bootcamp.fourthhomework.converter;

import com.n11bootcamp.fourthhomework.dto.CollectionDto;
import com.n11bootcamp.fourthhomework.entity.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface CollectionMapper {
    CollectionMapper INSTANCE = Mappers.getMapper(CollectionMapper.class);

    Collection convertToCollection(CollectionDto collectionDto);


    CollectionDto convertToCollectionDto(Collection collection);


    List<CollectionDto> convertAllToCollectionDto(List<Collection> collections);
}
