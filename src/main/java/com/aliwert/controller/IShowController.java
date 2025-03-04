package com.aliwert.controller;

import com.aliwert.dto.DtoShow;
import com.aliwert.dto.insert.DtoShowInsert;
import com.aliwert.dto.update.DtoShowUpdate;
import java.util.List;

public interface IShowController {

    RootEntity<List<DtoShow>> getAllShows();

    RootEntity<DtoShow> getShowById(Long id);

    RootEntity<DtoShow> createShow(DtoShowInsert dtoShowInsert);

    RootEntity<DtoShow> updateShow(Long id, DtoShowUpdate dtoShowUpdate);
    
    RootEntity<Void> deleteShow(Long id);
}