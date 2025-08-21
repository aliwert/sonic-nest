package com.aliwert.service.impl;

import com.aliwert.dto.DtoPodcastEpisode;
import com.aliwert.dto.insert.DtoPodcastEpisodeInsert;
import com.aliwert.dto.update.DtoPodcastEpisodeUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.PodcastEpisodeMapper;
import com.aliwert.model.Podcast;
import com.aliwert.model.PodcastEpisode;
import com.aliwert.repository.PodcastEpisodeRepository;
import com.aliwert.repository.PodcastRepository;
import com.aliwert.service.PodcastEpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PodcastEpisodeServiceImpl implements PodcastEpisodeService {

    private final PodcastEpisodeRepository podcastEpisodeRepository;
    private final PodcastRepository podcastRepository;
    private final PodcastEpisodeMapper podcastEpisodeMapper;

    @Override
    public List<DtoPodcastEpisode> getAllPodcastEpisodes() {
        return podcastEpisodeMapper.toDtoList(podcastEpisodeRepository.findAll());
    }

    @Override
    public List<DtoPodcastEpisode> getEpisodesByPodcastId(Long podcastId) {
        return podcastEpisodeMapper.toDtoList(podcastEpisodeRepository.findByPodcastId(podcastId));
    }

    @Override
    public DtoPodcastEpisode getPodcastEpisodeById(Long id) {
        PodcastEpisode episode = podcastEpisodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast Episode").prepareErrorMessage()));
        return podcastEpisodeMapper.toDto(episode);
    }

    @Override
    public DtoPodcastEpisode createPodcastEpisode(DtoPodcastEpisodeInsert dtoPodcastEpisodeInsert) {
        PodcastEpisode episode = podcastEpisodeMapper.toEntity(dtoPodcastEpisodeInsert);
        
        // set podcast relationship if provided
        if (dtoPodcastEpisodeInsert.getPodcastId() != null) {
            Podcast podcast = podcastRepository.findById(dtoPodcastEpisodeInsert.getPodcastId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast").prepareErrorMessage()));
            episode.setPodcast(podcast);
        }
        
        return podcastEpisodeMapper.toDto(podcastEpisodeRepository.save(episode));
    }

    @Override
    public DtoPodcastEpisode updatePodcastEpisode(Long id, DtoPodcastEpisodeUpdate dtoPodcastEpisodeUpdate) {
        PodcastEpisode episode = podcastEpisodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast Episode").prepareErrorMessage()));
        
        podcastEpisodeMapper.updateEntityFromDto(dtoPodcastEpisodeUpdate, episode);
        
        // update podcast relationship if provided
        if (dtoPodcastEpisodeUpdate.getPodcastId() != null) {
            Podcast podcast = podcastRepository.findById(dtoPodcastEpisodeUpdate.getPodcastId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast").prepareErrorMessage()));
            episode.setPodcast(podcast);
        }
        
        return podcastEpisodeMapper.toDto(podcastEpisodeRepository.save(episode));
    }

    @Override
    public void deletePodcastEpisode(Long id) {
        podcastEpisodeRepository.deleteById(id);
    }
}