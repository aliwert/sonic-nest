package com.aliwert.controller;

import com.aliwert.dto.DtoMarket;
import com.aliwert.dto.insert.DtoMarketInsert;
import com.aliwert.dto.update.DtoMarketUpdate;
import java.util.List;

public interface IMarketController {
    RootEntity<List<DtoMarket>> getAllMarkets();
    RootEntity<DtoMarket> getMarketById(Long id);
    RootEntity<DtoMarket> getMarketByCountryCode(String countryCode);
    RootEntity<DtoMarket> createMarket(DtoMarketInsert dtoMarketInsert);
    RootEntity<DtoMarket> updateMarket(Long id, DtoMarketUpdate dtoMarketUpdate);
    RootEntity<Void> deleteMarket(Long id);
}