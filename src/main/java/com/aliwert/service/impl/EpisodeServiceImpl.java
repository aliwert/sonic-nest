package com.aliwert.service.impl;

import com.aliwert.dto.DtoEpisode;
import com.aliwert.dto.insert.DtoEpisodeInsert;
import com.aliwert.dto.update.DtoEpisodeUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.model.Episode;
import com.aliwert.model.Show;
import com.aliwert.repository.EpisodeRepository;
import com.aliwert.repository.ShowRepository;
import com.aliwert.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final ShowRepository showRepository;
    private final ShowServiceImpl showService;

    @Override
    public List<DtoEpisode> getAllEpisodes() {
        return episodeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoEpisode getEpisodeById(Long id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Episode").prepareErrorMessage()));
        return convertToDto(episode);
    }

    @Override
    public DtoEpisode createEpisode(DtoEpisodeInsert dtoEpisodeInsert) {
        Episode episode = new Episode();
        updateEpisodeFromDto(episode, dtoEpisodeInsert);
        return convertToDto(episodeRepository.save(episode));
    }

    @Override
    public DtoEpisode updateEpisode(Long id, DtoEpisodeUpdate dtoEpisodeUpdate) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Episode").prepareErrorMessage()));
        updateEpisodeFromDto(episode, dtoEpisodeUpdate);
        return convertToDto(episodeRepository.save(episode));
    }

    @Override
    public void deleteEpisode(Long id) {
        episodeRepository.deleteById(id);
    }

    private DtoEpisode convertToDto(Episode episode) {
        DtoEpisode dto = new DtoEpisode();
        dto.setId(episode.getId());
        dto.setTitle(episode.getTitle());
        dto.setDescription(episode.getDescription());
        dto.setDuration(episode.getDuration());
        dto.setAudioUrl(episode.getAudioUrl());
        dto.setReleaseDate(episode.getReleaseDate());
        dto.setImageUrl(episode.getImageUrl());
        dto.setCreateTime(episode.getCreatedTime());
        dto.setShow(showService.convertToDto(episode.getShow()));
        return dto;
    }

    private void updateEpisodeFromDto(Episode episode, Object dto) {
        if (dto instanceof DtoEpisodeInsert insert) {
            updateEpisodeFields(episode, insert.getTitle(), insert.getDescription(), 
                insert.getDuration(), insert.getAudioUrl(), insert.getReleaseDate(), 
                insert.getImageUrl(), insert.getShowId());
        } else if (dto instanceof DtoEpisodeUpdate update) {
            updateEpisodeFields(episode, update.getTitle(), update.getDescription(), 
                update.getDuration(), update.getAudioUrl(), update.getReleaseDate(), 
                update.getImageUrl(), update.getShowId());
        }
    }

    private void updateEpisodeFields(Episode episode, String title, String description, 
            Integer duration, String audioUrl, LocalDateTime releaseDate, String imageUrl, Long showId) {
        episode.setTitle(title);
        episode.setDescription(description);
        episode.setDuration(duration);
        episode.setAudioUrl(audioUrl);
        episode.setReleaseDate(releaseDate);
        episode.setImageUrl(imageUrl);

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Show").prepareErrorMessage()));
        episode.setShow(show);
    }
}