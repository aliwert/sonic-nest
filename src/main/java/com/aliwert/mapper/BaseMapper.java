package com.aliwert.mapper;

import org.mapstruct.Named;

import java.sql.Date;

/**
 * base mapper interface containing common mapping methods
 * that can be used by all specific entity mappers
 */
public interface BaseMapper {

    @Named("dateToSqlDate")
    default Date dateToSqlDate(java.util.Date date) {
        if (date == null) {
            return null;
        }
        if (date instanceof Date) {
            return (Date) date;
        }
        return new Date(date.getTime());
    }
}
