package com.aliwert.controller;

import com.aliwert.dto.DtoPlayerState;
import com.aliwert.dto.insert.DtoPlayerStateInsert;
import com.aliwert.dto.update.DtoPlayerStateUpdate;
import java.util.List;

public interface IPlayerStateController {
    RootEntity<List<DtoPlayerState>> getAllPlayerStates();
    RootEntity<DtoPlayerState> getPlayerStateById(Long id);
    RootEntity<DtoPlayerState> getPlayerStateByUserId(Long userId);
    RootEntity<DtoPlayerState> createPlayerState(DtoPlayerStateInsert dtoPlayerStateInsert);
    RootEntity<DtoPlayerState> updatePlayerState(Long id, DtoPlayerStateUpdate dtoPlayerStateUpdate);
    RootEntity<Void> deletePlayerState(Long id);
}