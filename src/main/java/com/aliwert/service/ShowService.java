package com.aliwert.service;

import com.aliwert.dto.DtoShow;
import com.aliwert.dto.insert.DtoShowInsert;
import com.aliwert.dto.update.DtoShowUpdate;
import java.util.List;

public interface ShowService {

    List<DtoShow> getAllShows();

    DtoShow getShowById(Long id);

    DtoShow createShow(DtoShowInsert dtoShowInsert);

    DtoShow updateShow(Long id, DtoShowUpdate dtoShowUpdate);
    
    void deleteShow(Long id);
}