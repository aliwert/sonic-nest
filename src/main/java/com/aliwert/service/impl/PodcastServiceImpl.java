package com.aliwert.service.impl;

import com.aliwert.dto.DtoCategory;
import com.aliwert.dto.DtoPodcast;
import com.aliwert.dto.DtoPodcastEpisode;
import com.aliwert.dto.insert.DtoPodcastInsert;
import com.aliwert.dto.update.DtoPodcastUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.model.Category;
import com.aliwert.model.Podcast;
import com.aliwert.model.PodcastEpisode;
import com.aliwert.repository.CategoryRepository;
import com.aliwert.repository.PodcastRepository;
import com.aliwert.service.PodcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PodcastServiceImpl implements PodcastService {

    private final PodcastRepository podcastRepository;
    private final CategoryRepository categoryRepository;
    private final PodcastEpisodeServiceImpl podcastEpisodeService;

    @Override
    public List<DtoPodcast> getAllPodcasts() {
        return podcastRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoPodcast getPodcastById(Long id) {
        Podcast podcast = podcastRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast").prepareErrorMessage()));
        return convertToDto(podcast);
    }

    @Override
    public DtoPodcast createPodcast(DtoPodcastInsert dtoPodcastInsert) {
        Podcast podcast = new Podcast();
        updatePodcastFromDto(podcast, dtoPodcastInsert);
        return convertToDto(podcastRepository.save(podcast));
    }

    @Override
    public DtoPodcast updatePodcast(Long id, DtoPodcastUpdate dtoPodcastUpdate) {
        Podcast podcast = podcastRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast").prepareErrorMessage()));
        updatePodcastFromDto(podcast, dtoPodcastUpdate);
        return convertToDto(podcastRepository.save(podcast));
    }

    @Override
    public void deletePodcast(Long id) {
        podcastRepository.deleteById(id);
    }

    @Override
    public DtoPodcast addCategoryToPodcast(Long podcastId, Long categoryId) {
        Podcast podcast = podcastRepository.findById(podcastId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast").prepareErrorMessage()));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Category").prepareErrorMessage()));

        if (podcast.getCategories() == null) {
            podcast.setCategories(new ArrayList<>());
        }

        if (!podcast.getCategories().contains(category)) {
            podcast.getCategories().add(category);
            podcastRepository.save(podcast);
        }

        return convertToDto(podcast);
    }

    @Override
    public DtoPodcast removeCategoryFromPodcast(Long podcastId, Long categoryId) {
        Podcast podcast = podcastRepository.findById(podcastId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast").prepareErrorMessage()));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Category").prepareErrorMessage()));

        if (podcast.getCategories() != null) {
            podcast.getCategories().remove(category);
            podcastRepository.save(podcast);
        }

        return convertToDto(podcast);
    }

    public DtoPodcast convertToDto(Podcast podcast) {
        DtoPodcast dto = new DtoPodcast();
        dto.setId(podcast.getId());
        dto.setTitle(podcast.getTitle());
        dto.setAuthor(podcast.getAuthor());
        dto.setDescription(podcast.getDescription());
        dto.setPublisher(podcast.getPublisher());
        dto.setLanguage(podcast.getLanguage());
        dto.setExplicit(podcast.getExplicit());
        dto.setImageUrl(podcast.getImageUrl());

        // Handle creation time safely
        if (podcast.getCreatedTime() != null) {
            if (podcast.getCreatedTime() instanceof java.sql.Date) {
                dto.setCreateTime((java.sql.Date) podcast.getCreatedTime());
            } else {
                dto.setCreateTime(new java.sql.Date(podcast.getCreatedTime().getTime()));
            }
        }

        // Convert episodes to DTOs
        if (podcast.getEpisodes() != null && !podcast.getEpisodes().isEmpty()) {
            List<DtoPodcastEpisode> episodeDtos = podcast.getEpisodes().stream()
                    .map(podcastEpisodeService::convertToDto)
                    .collect(Collectors.toList());
            dto.setEpisodes(episodeDtos);
        }

        // Convert categories to DTOs
        if (podcast.getCategories() != null && !podcast.getCategories().isEmpty()) {
            List<DtoCategory> categoryDtos = podcast.getCategories().stream()
                    .map(category -> {
                        DtoCategory categoryDto = new DtoCategory();
                        categoryDto.setId(category.getId());
                        categoryDto.setName(category.getName());
                        categoryDto.setDescription(category.getDescription());
                        categoryDto.setImageUrl(category.getImageUrl());
                        return categoryDto;
                    })
                    .collect(Collectors.toList());
            dto.setCategories(categoryDtos);
        }

        return dto;
    }

    private void updatePodcastFromDto(Podcast podcast, Object dto) {
        if (dto instanceof DtoPodcastInsert insert) {
            podcast.setTitle(insert.getTitle());
            podcast.setAuthor(insert.getAuthor());
            podcast.setDescription(insert.getDescription());
            podcast.setPublisher(insert.getPublisher());
            podcast.setLanguage(insert.getLanguage());
            podcast.setExplicit(insert.getExplicit());
            podcast.setImageUrl(insert.getImageUrl());

            if (insert.getCategoryIds() != null && !insert.getCategoryIds().isEmpty()) {
                List<Category> categories = categoryRepository.findAllById(insert.getCategoryIds());
                podcast.setCategories(categories);
            }

        } else if (dto instanceof DtoPodcastUpdate update) {
            podcast.setTitle(update.getTitle());
            podcast.setAuthor(update.getAuthor());
            podcast.setDescription(update.getDescription());
            podcast.setPublisher(update.getPublisher());
            podcast.setLanguage(update.getLanguage());
            podcast.setExplicit(update.getExplicit());
            podcast.setImageUrl(update.getImageUrl());

            if (update.getCategoryIds() != null) {
                List<Category> categories = categoryRepository.findAllById(update.getCategoryIds());
                podcast.setCategories(categories);
            }
        }
    }
}