package com.aliwert.service;

import com.aliwert.dto.DtoPlaylist;
import com.aliwert.dto.insert.DtoPlaylistInsert;
import com.aliwert.dto.update.DtoPlaylistUpdate;
import java.util.List;

public interface PlaylistService {
    List<DtoPlaylist> getAllPlaylists();
    List<DtoPlaylist> getPublicPlaylists();
    List<DtoPlaylist> getPlaylistsByUserId(Long userId);
    DtoPlaylist getPlaylistById(Long id);
    DtoPlaylist createPlaylist(DtoPlaylistInsert dtoPlaylistInsert);
    DtoPlaylist updatePlaylist(Long id, DtoPlaylistUpdate dtoPlaylistUpdate);
    DtoPlaylist addTrackToPlaylist(Long playlistId, Long trackId);
    DtoPlaylist removeTrackFromPlaylist(Long playlistId, Long trackId);
    void deletePlaylist(Long id);
}