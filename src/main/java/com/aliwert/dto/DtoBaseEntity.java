package com.aliwert.dto;

import java.sql.Date;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class DtoBaseEntity {

    private Long id;

    private Date createTime;
}
