package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IPlaylistController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoPlaylist;
import com.aliwert.dto.insert.DtoPlaylistInsert;
import com.aliwert.dto.update.DtoPlaylistUpdate;
import com.aliwert.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlists")
@RequiredArgsConstructor
public class PlaylistControllerImpl extends BaseController implements IPlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoPlaylist>> getAllPlaylists() {
        return ok(playlistService.getAllPlaylists());
    }
    
    @GetMapping("/public")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoPlaylist>> getPublicPlaylists() {
        return ok(playlistService.getPublicPlaylists());
    }
    
    @GetMapping("/user/{userId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoPlaylist>> getPlaylistsByUserId(@PathVariable Long userId) {
        return ok(playlistService.getPlaylistsByUserId(userId));
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPlaylist> getPlaylistById(@PathVariable Long id) {
        return ok(playlistService.getPlaylistById(id));
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoPlaylist> createPlaylist(@RequestBody DtoPlaylistInsert dtoPlaylistInsert) {
        return ok(playlistService.createPlaylist(dtoPlaylistInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPlaylist> updatePlaylist(@PathVariable Long id, @RequestBody DtoPlaylistUpdate dtoPlaylistUpdate) {
        return ok(playlistService.updatePlaylist(id, dtoPlaylistUpdate));
    }
    
    @PostMapping("/{playlistId}/tracks/{trackId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPlaylist> addTrackToPlaylist(@PathVariable Long playlistId, @PathVariable Long trackId) {
        return ok(playlistService.addTrackToPlaylist(playlistId, trackId));
    }
    
    @DeleteMapping("/{playlistId}/tracks/{trackId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPlaylist> removeTrackFromPlaylist(@PathVariable Long playlistId, @PathVariable Long trackId) {
        return ok(playlistService.removeTrackFromPlaylist(playlistId, trackId));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return null;
    }
}