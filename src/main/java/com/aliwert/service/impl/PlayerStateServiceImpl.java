package com.aliwert.service.impl;

import com.aliwert.dto.DtoPlayerState;
import com.aliwert.dto.DtoUser;
import com.aliwert.dto.insert.DtoPlayerStateInsert;
import com.aliwert.dto.update.DtoPlayerStateUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerStateServiceImpl implements PlayerStateService {

    private final PlayerStateRepository playerStateRepository;
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;
    private final TrackServiceImpl trackService;

    @Override
    public List<DtoPlayerState> getAllPlayerStates() {
        return playerStateRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoPlayerState getPlayerStateById(Long id) {
        PlayerState playerState = playerStateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "PlayerState").prepareErrorMessage()));
        return convertToDto(playerState);
    }

    @Override
    public DtoPlayerState getPlayerStateByUserId(Long userId) {
        PlayerState playerState = playerStateRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "PlayerState for user").prepareErrorMessage()));
        return convertToDto(playerState);
    }

    @Override
    public DtoPlayerState createPlayerState(DtoPlayerStateInsert dtoPlayerStateInsert) {
        // check if player state already exists for user
        userRepository.findById(dtoPlayerStateInsert.getUserId()).ifPresent(user -> {
            if (playerStateRepository.findByUser(user).isPresent()) {
                throw new RuntimeException(new ErrorMessage(MessageType.ALREADY_EXISTS, "PlayerState for user").prepareErrorMessage());
            }
        });

        PlayerState playerState = new PlayerState();
        updatePlayerStateFromDto(playerState, dtoPlayerStateInsert);
        return convertToDto(playerStateRepository.save(playerState));
    }

    @Override
    public DtoPlayerState updatePlayerState(Long id, DtoPlayerStateUpdate dtoPlayerStateUpdate) {
        PlayerState playerState = playerStateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "PlayerState").prepareErrorMessage()));
        updatePlayerStateFromDto(playerState, dtoPlayerStateUpdate);
        return convertToDto(playerStateRepository.save(playerState));
    }

    @Override
    public void deletePlayerState(Long id) {
        playerStateRepository.deleteById(id);
    }

    public DtoPlayerState convertToDto(PlayerState playerState) {
        DtoPlayerState dto = new DtoPlayerState();
        dto.setId(playerState.getId());
        dto.setUser(convertUserToDto(playerState.getUser()));
        if (playerState.getCurrentTrack() != null) {
            dto.setCurrentTrack(trackService.convertToDto(playerState.getCurrentTrack()));
        }
        dto.setProgressMs(playerState.getProgressMs());
        dto.setIsPlaying(playerState.getIsPlaying());
        dto.setShuffleState(playerState.getShuffleState());
        dto.setRepeatState(playerState.getRepeatState());
        dto.setVolume(playerState.getVolume());

        // Handle creation time safely
        if (playerState.getCreatedTime() != null) {
            if (playerState.getCreatedTime() instanceof java.sql.Date) {
                dto.setCreateTime((java.sql.Date) playerState.getCreatedTime());
            } else {
                dto.setCreateTime(new java.sql.Date(playerState.getCreatedTime().getTime()));
            }
        }

        return dto;
    }

    private DtoUser convertUserToDto(User user) {
        if (user == null) return null;

        DtoUser dtoUser = new DtoUser();
        dtoUser.setId(user.getId());
        dtoUser.setUsername(user.getUsername());
        // Don't set password in DTO

        // Handle creation time safely
        if (user.getCreatedTime() != null) {
            if (user.getCreatedTime() instanceof java.sql.Date) {
                dtoUser.setCreateTime((java.sql.Date) user.getCreatedTime());
            } else {
                dtoUser.setCreateTime(new java.sql.Date(user.getCreatedTime().getTime()));
            }
        }

        return dtoUser;
    }

    private void updatePlayerStateFromDto(PlayerState playerState, Object dto) {
        if (dto instanceof DtoPlayerStateInsert insert) {
            updatePlayerStateFields(playerState, insert.getUserId(), insert.getCurrentTrackId(),
                    insert.getProgressMs(), insert.getIsPlaying(), insert.getShuffleState(),
                    insert.getRepeatState(), insert.getVolume());
        } else if (dto instanceof DtoPlayerStateUpdate update) {
            updatePlayerStateFields(playerState, update.getUserId(), update.getCurrentTrackId(),
                    update.getProgressMs(), update.getIsPlaying(), update.getShuffleState(),
                    update.getRepeatState(), update.getVolume());
        }
    }

    private void updatePlayerStateFields(PlayerState playerState, Long userId, Long trackId,
                                         Integer progressMs, Boolean isPlaying, Boolean shuffleState, String repeatState, Integer volume) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "User").prepareErrorMessage()));
        playerState.setUser(user);

        if (trackId != null) {
            Track track = trackRepository.findById(trackId)
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
            playerState.setCurrentTrack(track);
        } else {
            playerState.setCurrentTrack(null);
        }

        playerState.setProgressMs(progressMs);
        playerState.setIsPlaying(isPlaying);
        playerState.setShuffleState(shuffleState);
        playerState.setRepeatState(repeatState);
        playerState.setVolume(volume);
    }
}