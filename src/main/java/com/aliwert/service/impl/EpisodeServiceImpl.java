package com.aliwert.service.impl;

import com.aliwert.dto.DtoEpisode;
import com.aliwert.dto.insert.DtoEpisodeInsert;
import com.aliwert.dto.update.DtoEpisodeUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.EpisodeMapper;
import com.aliwert.model.Episode;
import com.aliwert.model.Show;
import com.aliwert.repository.EpisodeRepository;
import com.aliwert.repository.ShowRepository;
import com.aliwert.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final ShowRepository showRepository;
    private final EpisodeMapper episodeMapper;

    @Override
    public List<DtoEpisode> getAllEpisodes() {
        return episodeMapper.toDtoList(episodeRepository.findAll());
    }

    @Override
    public DtoEpisode getEpisodeById(Long id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Episode").prepareErrorMessage()));
        return episodeMapper.toDto(episode);
    }

    @Override
    public DtoEpisode createEpisode(DtoEpisodeInsert dtoEpisodeInsert) {
        Episode episode = episodeMapper.toEntity(dtoEpisodeInsert);
        
        // set show if provided
        if (dtoEpisodeInsert.getShowId() != null) {
            Show show = showRepository.findById(dtoEpisodeInsert.getShowId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Show").prepareErrorMessage()));
            episode.setShow(show);
        }
        
        return episodeMapper.toDto(episodeRepository.save(episode));
    }

    @Override
    public DtoEpisode updateEpisode(Long id, DtoEpisodeUpdate dtoEpisodeUpdate) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Episode").prepareErrorMessage()));
        
        episodeMapper.updateEntityFromDto(dtoEpisodeUpdate, episode);
        
        // update show if provided
        if (dtoEpisodeUpdate.getShowId() != null) {
            Show show = showRepository.findById(dtoEpisodeUpdate.getShowId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Show").prepareErrorMessage()));
            episode.setShow(show);
        }
        
        return episodeMapper.toDto(episodeRepository.save(episode));
    }

    @Override
    public void deleteEpisode(Long id) {
        episodeRepository.deleteById(id);
    }
}