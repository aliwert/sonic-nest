package com.aliwert.service.impl;

import com.aliwert.dto.DtoPodcast;
import com.aliwert.dto.insert.DtoPodcastInsert;
import com.aliwert.dto.update.DtoPodcastUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.PodcastMapper;
import com.aliwert.model.Category;
import com.aliwert.model.Podcast;
import com.aliwert.repository.CategoryRepository;
import com.aliwert.repository.PodcastRepository;
import com.aliwert.service.PodcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PodcastServiceImpl implements PodcastService {

    private final PodcastRepository podcastRepository;
    private final CategoryRepository categoryRepository;
    private final PodcastMapper podcastMapper;

    @Override
    public List<DtoPodcast> getAllPodcasts() {
        return podcastMapper.toDtoList(podcastRepository.findAll());
    }

    @Override
    public DtoPodcast getPodcastById(Long id) {
        Podcast podcast = podcastRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast").prepareErrorMessage()));
        return podcastMapper.toDto(podcast);
    }

    @Override
    public DtoPodcast createPodcast(DtoPodcastInsert dtoPodcastInsert) {
        Podcast podcast = podcastMapper.toEntity(dtoPodcastInsert);
        
        // handle categories if provided
        if (dtoPodcastInsert.getCategoryIds() != null && !dtoPodcastInsert.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(dtoPodcastInsert.getCategoryIds());
            podcast.setCategories(categories);
        }
        
        return podcastMapper.toDto(podcastRepository.save(podcast));
    }

    @Override
    public DtoPodcast updatePodcast(Long id, DtoPodcastUpdate dtoPodcastUpdate) {
        Podcast podcast = podcastRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Podcast").prepareErrorMessage()));
        
        podcastMapper.updateEntityFromDto(dtoPodcastUpdate, podcast);
        
        // handle categories if provided
        if (dtoPodcastUpdate.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(dtoPodcastUpdate.getCategoryIds());
            podcast.setCategories(categories);
        }
        
        return podcastMapper.toDto(podcastRepository.save(podcast));
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

        return podcastMapper.toDto(podcast);
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

        return podcastMapper.toDto(podcast);
    }
}