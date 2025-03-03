package com.aliwert.controller;

import com.aliwert.dto.DtoPlaylist;
import com.aliwert.dto.insert.DtoPlaylistInsert;
import com.aliwert.dto.update.DtoPlaylistUpdate;
import java.util.List;

public interface IPlaylistController {
    RootEntity<List<DtoPlaylist>> getAllPlaylists();
    RootEntity<List<DtoPlaylist>> getPublicPlaylists();
    RootEntity<List<DtoPlaylist>> getPlaylistsByUserId(Long userId);
    RootEntity<DtoPlaylist> getPlaylistById(Long id);
    RootEntity<DtoPlaylist> createPlaylist(DtoPlaylistInsert dtoPlaylistInsert);
    RootEntity<DtoPlaylist> updatePlaylist(Long id, DtoPlaylistUpdate dtoPlaylistUpdate);
    RootEntity<DtoPlaylist> addTrackToPlaylist(Long playlistId, Long trackId);
    RootEntity<DtoPlaylist> removeTrackFromPlaylist(Long playlistId, Long trackId);
    RootEntity<Void> deletePlaylist(Long id);
}