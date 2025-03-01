package com.aliwert.repository;

import com.aliwert.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {
    // method to find by country code
    Market findByCountryCode(String countryCode);
}