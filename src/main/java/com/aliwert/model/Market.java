package com.aliwert.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "markets")
@NoArgsConstructor
@AllArgsConstructor
public class Market extends BaseEntity {
    private String countryCode;
    private String countryName;
    private String currency;
}