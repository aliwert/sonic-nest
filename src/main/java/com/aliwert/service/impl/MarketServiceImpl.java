package com.aliwert.service.impl;

import com.aliwert.dto.DtoMarket;
import com.aliwert.dto.insert.DtoMarketInsert;
import com.aliwert.dto.update.DtoMarketUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.MarketMapper;
import com.aliwert.model.Market;
import com.aliwert.repository.MarketRepository;
import com.aliwert.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {

    private final MarketRepository marketRepository;
    private final MarketMapper marketMapper;

    @Override
    public List<DtoMarket> getAllMarkets() {
        return marketMapper.toDtoList(marketRepository.findAll());
    }

    @Override
    public DtoMarket getMarketById(Long id) {
        Market market = marketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Market").prepareErrorMessage()));
        return marketMapper.toDto(market);
    }

    @Override
    public DtoMarket getMarketByCountryCode(String countryCode) {
        Market market = marketRepository.findByCountryCode(countryCode);
        if (market == null) {
            throw new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Market with country code " + countryCode).prepareErrorMessage());
        }
        return marketMapper.toDto(market);
    }

    @Override
    public DtoMarket createMarket(DtoMarketInsert dtoMarketInsert) {
        Market market = marketMapper.toEntity(dtoMarketInsert);
        return marketMapper.toDto(marketRepository.save(market));
    }

    @Override
    public DtoMarket updateMarket(Long id, DtoMarketUpdate dtoMarketUpdate) {
        Market market = marketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Market").prepareErrorMessage()));
        marketMapper.updateEntityFromDto(dtoMarketUpdate, market);
        return marketMapper.toDto(marketRepository.save(market));
    }

    @Override
    public void deleteMarket(Long id) {
        marketRepository.deleteById(id);
    }
}