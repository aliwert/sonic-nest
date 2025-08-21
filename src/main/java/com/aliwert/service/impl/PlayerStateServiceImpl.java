package com.aliwert.service.impl;

import com.aliwert.dto.DtoPlayerState;
import com.aliwert.dto.insert.DtoPlayerStateInsert;
import com.aliwert.dto.update.DtoPlayerStateUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.PlayerStateMapper;
import com.aliwert.model.PlayerState;
import com.aliwert.model.Track;
import com.aliwert.model.User;
import com.aliwert.repository.PlayerStateRepository;
import com.aliwert.repository.TrackRepository;
import com.aliwert.repository.UserRepository;
import com.aliwert.service.PlayerStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerStateServiceImpl implements PlayerStateService {

    private final PlayerStateRepository playerStateRepository;
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;
    private final PlayerStateMapper playerStateMapper;

    @Override
    public List<DtoPlayerState> getAllPlayerStates() {
        return playerStateMapper.toDtoList(playerStateRepository.findAll());
    }

    @Override
    public DtoPlayerState getPlayerStateById(Long id) {
        PlayerState playerState = playerStateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "PlayerState").prepareErrorMessage()));
        return playerStateMapper.toDto(playerState);
    }

    @Override
    public DtoPlayerState getPlayerStateByUserId(Long userId) {
        PlayerState playerState = playerStateRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "PlayerState for user").prepareErrorMessage()));
        return playerStateMapper.toDto(playerState);
    }

    @Override
    public DtoPlayerState createPlayerState(DtoPlayerStateInsert dtoPlayerStateInsert) {
        // check if player state already exists for user
        userRepository.findById(dtoPlayerStateInsert.getUserId()).ifPresent(user -> {
            if (playerStateRepository.findByUser(user).isPresent()) {
                throw new RuntimeException(new ErrorMessage(MessageType.ALREADY_EXISTS, "PlayerState for user").prepareErrorMessage());
            }
        });

        PlayerState playerState = playerStateMapper.toEntity(dtoPlayerStateInsert);
        
        if (dtoPlayerStateInsert.getUserId() != null) {
            User user = userRepository.findById(dtoPlayerStateInsert.getUserId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "User").prepareErrorMessage()));
            playerState.setUser(user);
        }
        
        if (dtoPlayerStateInsert.getCurrentTrackId() != null) {
            Track track = trackRepository.findById(dtoPlayerStateInsert.getCurrentTrackId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
            playerState.setCurrentTrack(track);
        }
        
        return playerStateMapper.toDto(playerStateRepository.save(playerState));
    }

    @Override
    public DtoPlayerState updatePlayerState(Long id, DtoPlayerStateUpdate dtoPlayerStateUpdate) {
        PlayerState playerState = playerStateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "PlayerState").prepareErrorMessage()));
        
        playerStateMapper.updateEntityFromDto(dtoPlayerStateUpdate, playerState);
        
        if (dtoPlayerStateUpdate.getUserId() != null) {
            User user = userRepository.findById(dtoPlayerStateUpdate.getUserId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "User").prepareErrorMessage()));
            playerState.setUser(user);
        }
        
        if (dtoPlayerStateUpdate.getCurrentTrackId() != null) {
            Track track = trackRepository.findById(dtoPlayerStateUpdate.getCurrentTrackId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
            playerState.setCurrentTrack(track);
        }
        
        return playerStateMapper.toDto(playerStateRepository.save(playerState));
    }

    @Override
    public void deletePlayerState(Long id) {
        playerStateRepository.deleteById(id);
    }
}