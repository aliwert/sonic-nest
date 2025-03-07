package com.aliwert.service.impl;

import com.aliwert.dto.DtoMarket;
import com.aliwert.dto.insert.DtoMarketInsert;
import com.aliwert.dto.update.DtoMarketUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.model.Market;
import com.aliwert.repository.MarketRepository;
import com.aliwert.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {

    private final MarketRepository marketRepository;

    @Override
    public List<DtoMarket> getAllMarkets() {
        return marketRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoMarket getMarketById(Long id) {
        Market market = marketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Market").prepareErrorMessage()));
        return convertToDto(market);
    }

    @Override
    public DtoMarket getMarketByCountryCode(String countryCode) {
        Market market = marketRepository.findByCountryCode(countryCode);
        if (market == null) {
            throw new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Market with country code " + countryCode).prepareErrorMessage());
        }
        return convertToDto(market);
    }

    @Override
    public DtoMarket createMarket(DtoMarketInsert dtoMarketInsert) {
        Market market = new Market();
        updateMarketFromDto(market, dtoMarketInsert);
        return convertToDto(marketRepository.save(market));
    }

    @Override
    public DtoMarket updateMarket(Long id, DtoMarketUpdate dtoMarketUpdate) {
        Market market = marketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Market").prepareErrorMessage()));
        updateMarketFromDto(market, dtoMarketUpdate);
        return convertToDto(marketRepository.save(market));
    }

    @Override
    public void deleteMarket(Long id) {
        marketRepository.deleteById(id);
    }

    private DtoMarket convertToDto(Market market) {
        DtoMarket dto = new DtoMarket();
        dto.setId(market.getId());
        dto.setCountryCode(market.getCountryCode());
        dto.setCountryName(market.getCountryName());
        dto.setCurrency(market.getCurrency());

        // Handle creation time safely
        if (market.getCreatedTime() != null) {
            if (market.getCreatedTime() instanceof java.sql.Date) {
                dto.setCreateTime((java.sql.Date) market.getCreatedTime());
            } else {
                dto.setCreateTime(new java.sql.Date(market.getCreatedTime().getTime()));
            }
        }

        return dto;
    }

    private void updateMarketFromDto(Market market, Object dto) {
        if (dto instanceof DtoMarketInsert insert) {
            market.setCountryCode(insert.getCountryCode());
            market.setCountryName(insert.getCountryName());
            market.setCurrency(insert.getCurrency());
        } else if (dto instanceof DtoMarketUpdate update) {
            market.setCountryCode(update.getCountryCode());
            market.setCountryName(update.getCountryName());
            market.setCurrency(update.getCurrency());
        }
    }
}