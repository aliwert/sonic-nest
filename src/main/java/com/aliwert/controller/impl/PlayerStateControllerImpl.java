package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IPlayerStateController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoPlayerState;
import com.aliwert.dto.insert.DtoPlayerStateInsert;
import com.aliwert.dto.update.DtoPlayerStateUpdate;
import com.aliwert.service.PlayerStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/player-states")
@RequiredArgsConstructor
public class PlayerStateControllerImpl extends BaseController implements IPlayerStateController {

    private final PlayerStateService playerStateService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoPlayerState>> getAllPlayerStates() {
        return ok(playerStateService.getAllPlayerStates());
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPlayerState> getPlayerStateById(@PathVariable Long id) {
        return ok(playerStateService.getPlayerStateById(id));
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoPlayerState> createPlayerState(@RequestBody DtoPlayerStateInsert dtoPlayerStateInsert) {
        return ok(playerStateService.createPlayerState(dtoPlayerStateInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPlayerState> updatePlayerState(@PathVariable Long id, @RequestBody DtoPlayerStateUpdate dtoPlayerStateUpdate) {
        return ok(playerStateService.updatePlayerState(id, dtoPlayerStateUpdate));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deletePlayerState(@PathVariable Long id) {
        playerStateService.deletePlayerState(id);
        return null;
    }
    
    @GetMapping("/user/{userId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPlayerState> getPlayerStateByUserId(@PathVariable Long userId) {
        return ok(playerStateService.getPlayerStateByUserId(userId));
    }
}