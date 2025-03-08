package com.aliwert.service.impl;

import com.aliwert.dto.DtoPodcastEpisode;
import com.aliwert.dto.insert.DtoPodcastEpisodeInsert;
import com.aliwert.dto.update.DtoPodcastEpisodeUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.model.Podcast;
import com.aliwert.model.PodcastEpisode;
import com.aliwert.repository.PodcastEpisodeRepository;
import com.aliwert.repository.PodcastRepository;
import com.aliwert.service.PodcastEpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PodcastEpisodeServiceImpl implements PodcastEpisodeService {

    private final PodcastEpisodeRepository podcastEpisodeRepository;
    private final PodcastRepository podcastRepository;

    @Override
    public List<DtoPodcastEpisode> getAllPodcastEpisodes() {
        return podcastEpisodeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoPodcastEpisode> getEpisodesByPodcastId(Long podcastId) {
        return podcastEpisodeRepository.findByPodcastId(podcastId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoPodcastEpisode getPodcastEpisodeById(Long id) {
        PodcastEpisode episode = podcastEpisodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast Episode").prepareErrorMessage()));
        return convertToDto(episode);
    }

    @Override
    public DtoPodcastEpisode createPodcastEpisode(DtoPodcastEpisodeInsert dtoPodcastEpisodeInsert) {
        PodcastEpisode episode = new PodcastEpisode();
        updateEpisodeFromDto(episode, dtoPodcastEpisodeInsert);
        return convertToDto(podcastEpisodeRepository.save(episode));
    }

    @Override
    public DtoPodcastEpisode updatePodcastEpisode(Long id, DtoPodcastEpisodeUpdate dtoPodcastEpisodeUpdate) {
        PodcastEpisode episode = podcastEpisodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast Episode").prepareErrorMessage()));
        updateEpisodeFromDto(episode, dtoPodcastEpisodeUpdate);
        return convertToDto(podcastEpisodeRepository.save(episode));
    }

    @Override
    public void deletePodcastEpisode(Long id) {
        podcastEpisodeRepository.deleteById(id);
    }

    public DtoPodcastEpisode convertToDto(PodcastEpisode episode) {
        DtoPodcastEpisode dto = new DtoPodcastEpisode();
        dto.setId(episode.getId());
        dto.setTitle(episode.getTitle());
        dto.setDescription(episode.getDescription());
        dto.setDuration(episode.getDuration());
        dto.setAudioUrl(episode.getAudioUrl());
        dto.setReleaseDate(episode.getReleaseDate());
        dto.setImageUrl(episode.getImageUrl());
        dto.setEpisodeNumber(episode.getEpisodeNumber());
        dto.setExplicit(episode.getExplicit());

        // handle creation
        if (episode.getCreatedTime() != null) {
            if (episode.getCreatedTime() instanceof java.sql.Date) {
                dto.setCreateTime((java.sql.Date) episode.getCreatedTime());
            } else {
                dto.setCreateTime(new java.sql.Date(episode.getCreatedTime().getTime()));
            }
        }

        return dto;
    }

    private void updateEpisodeFromDto(PodcastEpisode episode, Object dto) {
        if (dto instanceof DtoPodcastEpisodeInsert insert) {
            updateEpisodeFields(episode, insert.getTitle(), insert.getDescription(),
                    insert.getDuration(), insert.getAudioUrl(), insert.getReleaseDate(),
                    insert.getImageUrl(), insert.getEpisodeNumber(), insert.getExplicit(), insert.getPodcastId());
        } else if (dto instanceof DtoPodcastEpisodeUpdate update) {
            updateEpisodeFields(episode, update.getTitle(), update.getDescription(),
                    update.getDuration(), update.getAudioUrl(), update.getReleaseDate(),
                    update.getImageUrl(), update.getEpisodeNumber(), update.getExplicit(), update.getPodcastId());
        }
    }

    private void updateEpisodeFields(PodcastEpisode episode, String title, String description,
                                     Integer duration, String audioUrl, LocalDateTime releaseDate,
                                     String imageUrl, Integer episodeNumber, Boolean explicit, Long podcastId) {
        episode.setTitle(title);
        episode.setDescription(description);
        episode.setDuration(duration);
        episode.setAudioUrl(audioUrl);
        episode.setReleaseDate(releaseDate);
        episode.setImageUrl(imageUrl);
        episode.setEpisodeNumber(episodeNumber);
        episode.setExplicit(explicit);

        // handle podcast
        if (podcastId != null) {
            Podcast podcast = podcastRepository.findById(podcastId)
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast").prepareErrorMessage()));
            episode.setPodcast(podcast);
        }
    }
}