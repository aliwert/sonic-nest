package com.aliwert.service;

import com.aliwert.dto.DtoMarket;
import com.aliwert.dto.insert.DtoMarketInsert;
import com.aliwert.dto.update.DtoMarketUpdate;
import java.util.List;

public interface MarketService {
    List<DtoMarket> getAllMarkets();
    DtoMarket getMarketById(Long id);
    DtoMarket getMarketByCountryCode(String countryCode);
    DtoMarket createMarket(DtoMarketInsert dtoMarketInsert);
    DtoMarket updateMarket(Long id, DtoMarketUpdate dtoMarketUpdate);
    void deleteMarket(Long id);
}