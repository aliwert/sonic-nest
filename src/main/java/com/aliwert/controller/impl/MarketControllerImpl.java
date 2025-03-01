package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IMarketController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoMarket;
import com.aliwert.dto.insert.DtoMarketInsert;
import com.aliwert.dto.update.DtoMarketUpdate;
import com.aliwert.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/markets")
@RequiredArgsConstructor
public class MarketControllerImpl extends BaseController implements IMarketController {

    private final MarketService marketService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoMarket>> getAllMarkets() {
        return ok(marketService.getAllMarkets());
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoMarket> getMarketById(@PathVariable Long id) {
        return ok(marketService.getMarketById(id));
    }
    
    @GetMapping("/code/{countryCode}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoMarket> getMarketByCountryCode(@PathVariable String countryCode) {
        return ok(marketService.getMarketByCountryCode(countryCode));
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoMarket> createMarket(@RequestBody DtoMarketInsert dtoMarketInsert) {
        return ok(marketService.createMarket(dtoMarketInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoMarket> updateMarket(@PathVariable Long id, @RequestBody DtoMarketUpdate dtoMarketUpdate) {
        return ok(marketService.updateMarket(id, dtoMarketUpdate));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deleteMarket(@PathVariable Long id) {
        marketService.deleteMarket(id);
        return null;
    }
}