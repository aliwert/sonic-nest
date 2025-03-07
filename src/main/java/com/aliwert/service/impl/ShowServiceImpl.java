package com.aliwert.service.impl;

import com.aliwert.dto.DtoEpisode;
import com.aliwert.dto.DtoShow;
import com.aliwert.dto.insert.DtoShowInsert;
import com.aliwert.dto.update.DtoShowUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.model.Episode;
import com.aliwert.model.Show;
import com.aliwert.repository.ShowRepository;
import com.aliwert.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;

    @Override
    public List<DtoShow> getAllShows() {
        return showRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoShow getShowById(Long id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Show").prepareErrorMessage()));
        return convertToDto(show);
    }

    @Override
    public DtoShow createShow(DtoShowInsert dtoShowInsert) {
        Show show = new Show();
        updateShowFromDto(show, dtoShowInsert);
        return convertToDto(showRepository.save(show));
    }

    @Override
    public DtoShow updateShow(Long id, DtoShowUpdate dtoShowUpdate) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Show").prepareErrorMessage()));
        updateShowFromDto(show, dtoShowUpdate);
        return convertToDto(showRepository.save(show));
    }

    @Override
    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }

    public DtoShow convertToDto(Show show) {
        DtoShow dto = new DtoShow();
        dto.setId(show.getId());
        dto.setTitle(show.getTitle());
        dto.setDescription(show.getDescription());
        dto.setPublisher(show.getPublisher());
        dto.setImageUrl(show.getImageUrl());

        // Handle creation time safely
        if (show.getCreatedTime() != null) {
            if (show.getCreatedTime() instanceof java.sql.Date) {
                dto.setCreateTime((java.sql.Date) show.getCreatedTime());
            } else {
                dto.setCreateTime(new java.sql.Date(show.getCreatedTime().getTime()));
            }
        }

        // Convert episodes to DTOs while avoiding circular references
        if (show.getEpisodes() != null) {
            List<DtoEpisode> episodeDtos = new ArrayList<>();
            for (Episode episode : show.getEpisodes()) {
                DtoEpisode episodeDto = new DtoEpisode();
                episodeDto.setId(episode.getId());
                episodeDto.setTitle(episode.getTitle());
                episodeDto.setDescription(episode.getDescription());
                episodeDto.setDuration(episode.getDuration());
                episodeDto.setAudioUrl(episode.getAudioUrl());
                episodeDto.setReleaseDate(episode.getReleaseDate());
                episodeDto.setImageUrl(episode.getImageUrl());

                // Handle episode creation time safely
                if (episode.getCreatedTime() != null) {
                    if (episode.getCreatedTime() instanceof java.sql.Date) {
                        episodeDto.setCreateTime((java.sql.Date) episode.getCreatedTime());
                    } else {
                        episodeDto.setCreateTime(new java.sql.Date(episode.getCreatedTime().getTime()));
                    }
                }

                // Don't set show to avoid circular reference
                episodeDtos.add(episodeDto);
            }
            dto.setEpisodes(episodeDtos);
        }

        return dto;
    }

    private void updateShowFromDto(Show show, Object dto) {
        if (dto instanceof DtoShowInsert insert) {
            show.setTitle(insert.getTitle());
            show.setDescription(insert.getDescription());
            show.setPublisher(insert.getPublisher());
            show.setImageUrl(insert.getImageUrl());
        } else if (dto instanceof DtoShowUpdate update) {
            show.setTitle(update.getTitle());
            show.setDescription(update.getDescription());
            show.setPublisher(update.getPublisher());
            show.setImageUrl(update.getImageUrl());
        }
    }
}