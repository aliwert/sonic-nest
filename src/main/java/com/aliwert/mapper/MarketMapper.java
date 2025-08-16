package com.aliwert.mapper;

import com.aliwert.dto.DtoMarket;
import com.aliwert.dto.insert.DtoMarketInsert;
import com.aliwert.dto.update.DtoMarketUpdate;
import com.aliwert.model.Market;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MarketMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    DtoMarket toDto(Market market);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    Market toEntity(DtoMarketInsert dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    void updateEntityFromDto(DtoMarketUpdate dto, @MappingTarget Market market);

    List<DtoMarket> toDtoList(List<Market> markets);
}
