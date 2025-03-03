package com.aliwert.service.impl;

import com.aliwert.dto.DtoPlaylist;
import com.aliwert.dto.DtoTrack;
import com.aliwert.dto.DtoUser;
import com.aliwert.dto.insert.DtoPlaylistInsert;
import com.aliwert.dto.update.DtoPlaylistUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.model.Playlist;
import com.aliwert.model.Track;
import com.aliwert.model.User;
import com.aliwert.repository.PlaylistRepository;
import com.aliwert.repository.TrackRepository;
import com.aliwert.repository.UserRepository;
import com.aliwert.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;
    private final TrackServiceImpl trackService;

    @Override
    public List<DtoPlaylist> getAllPlaylists() {
        return playlistRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoPlaylist> getPublicPlaylists() {
        return playlistRepository.findByIsPublicTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoPlaylist> getPlaylistsByUserId(Long userId) {
        return playlistRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoPlaylist getPlaylistById(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Playlist").prepareErrorMessage()));
        return convertToDto(playlist);
    }

    @Override
    public DtoPlaylist createPlaylist(DtoPlaylistInsert dtoPlaylistInsert) {
        Playlist playlist = new Playlist();
        updatePlaylistFromDto(playlist, dtoPlaylistInsert);
        return convertToDto(playlistRepository.save(playlist));
    }

    @Override
    public DtoPlaylist updatePlaylist(Long id, DtoPlaylistUpdate dtoPlaylistUpdate) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Playlist").prepareErrorMessage()));
        updatePlaylistFromDto(playlist, dtoPlaylistUpdate);
        return convertToDto(playlistRepository.save(playlist));
    }
    
    @Override
    public DtoPlaylist addTrackToPlaylist(Long playlistId, Long trackId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Playlist").prepareErrorMessage()));
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
        
        if (playlist.getTracks() == null) {
            playlist.setTracks(new ArrayList<>());
        }
        
        if (!playlist.getTracks().contains(track)) {
            playlist.getTracks().add(track);
            playlistRepository.save(playlist);
        }
        
        return convertToDto(playlist);
    }
    
    @Override
    public DtoPlaylist removeTrackFromPlaylist(Long playlistId, Long trackId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Playlist").prepareErrorMessage()));
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Track").prepareErrorMessage()));
        
        if (playlist.getTracks() != null) {
            playlist.getTracks().remove(track);
            playlistRepository.save(playlist);
        }
        
        return convertToDto(playlist);
    }

    @Override
    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }

    private DtoPlaylist convertToDto(Playlist playlist) {
        DtoPlaylist dto = new DtoPlaylist();
        dto.setId(playlist.getId());
        dto.setName(playlist.getName());
        dto.setDescription(playlist.getDescription());
        dto.setPublic(playlist.isPublic());
        dto.setCreateTime((Date) playlist.getCreatedTime());
        
        // Convert user to DTO
        if (playlist.getUser() != null) {
            dto.setUser(convertUserToDto(playlist.getUser()));
        }
        
        // convert tracks to dto
        if (playlist.getTracks() != null) {
            dto.setTracks(playlist.getTracks().stream()
                    .map(trackService::convertToDto)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    private DtoUser convertUserToDto(User user) {
        if (user == null) return null;
        
        DtoUser dtoUser = new DtoUser();
        dtoUser.setId(user.getId());
        dtoUser.setUsername(user.getUsername());
        // don t set pass, security problems
        dtoUser.setCreateTime((Date) user.getCreatedTime());
        return dtoUser;
    }

    private void updatePlaylistFromDto(Playlist playlist, Object dto) {
        if (dto instanceof DtoPlaylistInsert insert) {
            playlist.setName(insert.getName());
            playlist.setDescription(insert.getDescription());
            playlist.setPublic(insert.isPublic());
            
            // set user
            User user = userRepository.findById(insert.getUserId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "User").prepareErrorMessage()));
            playlist.setUser(user);
            
            // set tracks if provided
            if (insert.getTrackIds() != null && !insert.getTrackIds().isEmpty()) {
                List<Track> tracks = trackRepository.findAllById(insert.getTrackIds());
                playlist.setTracks(tracks);
            }
            
        } else if (dto instanceof DtoPlaylistUpdate update) {
            playlist.setName(update.getName());
            playlist.setDescription(update.getDescription());
            playlist.setPublic(update.isPublic());
            
            // update user if provided
            if (update.getUserId() != null) {
                User user = userRepository.findById(update.getUserId())
                        .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "User").prepareErrorMessage()));
                playlist.setUser(user);
            }
            
            // update tracks if provided
            if (update.getTrackIds() != null) {
                List<Track> tracks = trackRepository.findAllById(update.getTrackIds());
                playlist.setTracks(tracks);
            }
        }
    }
}