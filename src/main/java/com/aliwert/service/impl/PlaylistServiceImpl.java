package com.aliwert.service.impl;

import com.aliwert.dto.DtoPlaylist;
import com.aliwert.dto.insert.DtoPlaylistInsert;
import com.aliwert.dto.update.DtoPlaylistUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.PlaylistMapper;
import com.aliwert.model.Playlist;
import com.aliwert.model.Track;
import com.aliwert.model.User;
import com.aliwert.repository.PlaylistRepository;
import com.aliwert.repository.TrackRepository;
import com.aliwert.repository.UserRepository;
import com.aliwert.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;
    private final PlaylistMapper playlistMapper;

    @Override
    public List<DtoPlaylist> getAllPlaylists() {
        return playlistMapper.toDtoList(playlistRepository.findAll());
    }

    @Override
    public List<DtoPlaylist> getPublicPlaylists() {
        return playlistMapper.toDtoList(playlistRepository.findByIsPublicTrue());
    }

    @Override
    public List<DtoPlaylist> getPlaylistsByUserId(Long userId) {
        return playlistMapper.toDtoList(playlistRepository.findByUserId(userId));
    }

    @Override
    public DtoPlaylist getPlaylistById(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Playlist").prepareErrorMessage()));
        return playlistMapper.toDto(playlist);
    }

    @Override
    public DtoPlaylist createPlaylist(DtoPlaylistInsert dtoPlaylistInsert) {
        Playlist playlist = playlistMapper.toEntity(dtoPlaylistInsert);
        
        // set user
        User user = userRepository.findById(dtoPlaylistInsert.getUserId())
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "User").prepareErrorMessage()));
        playlist.setUser(user);

        // set tracks if provided
        if (dtoPlaylistInsert.getTrackIds() != null && !dtoPlaylistInsert.getTrackIds().isEmpty()) {
            List<Track> tracks = trackRepository.findAllById(dtoPlaylistInsert.getTrackIds());
            playlist.setTracks(tracks);
        }
        
        return playlistMapper.toDto(playlistRepository.save(playlist));
    }

    @Override
    public DtoPlaylist updatePlaylist(Long id, DtoPlaylistUpdate dtoPlaylistUpdate) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Playlist").prepareErrorMessage()));
        
        playlistMapper.updateEntityFromDto(dtoPlaylistUpdate, playlist);
        
        // update user if provided
        if (dtoPlaylistUpdate.getUserId() != null) {
            User user = userRepository.findById(dtoPlaylistUpdate.getUserId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "User").prepareErrorMessage()));
            playlist.setUser(user);
        }

        // update tracks if provided
        if (dtoPlaylistUpdate.getTrackIds() != null) {
            List<Track> tracks = trackRepository.findAllById(dtoPlaylistUpdate.getTrackIds());
            playlist.setTracks(tracks);
        }
        
        return playlistMapper.toDto(playlistRepository.save(playlist));
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

        return playlistMapper.toDto(playlist);
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

        return playlistMapper.toDto(playlist);
    }

    @Override
    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }
}