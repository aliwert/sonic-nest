package com.aliwert.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "markets")
@NoArgsConstructor
@AllArgsConstructor
public class Market extends BaseEntity {
    private String countryCode;
    private String countryName;
    private String currency;
}