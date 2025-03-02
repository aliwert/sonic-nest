package com.aliwert.service;

import com.aliwert.dto.DtoPlayerState;
import com.aliwert.dto.insert.DtoPlayerStateInsert;
import com.aliwert.dto.update.DtoPlayerStateUpdate;
import java.util.List;

public interface PlayerStateService {
    List<DtoPlayerState> getAllPlayerStates();
    DtoPlayerState getPlayerStateById(Long id);
    DtoPlayerState getPlayerStateByUserId(Long userId);
    DtoPlayerState createPlayerState(DtoPlayerStateInsert dtoPlayerStateInsert);
    DtoPlayerState updatePlayerState(Long id, DtoPlayerStateUpdate dtoPlayerStateUpdate);
    void deletePlayerState(Long id);
}