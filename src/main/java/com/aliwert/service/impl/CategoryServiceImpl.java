package com.aliwert.service.impl;

import com.aliwert.dto.DtoCategory;
import com.aliwert.dto.insert.DtoCategoryInsert;
import com.aliwert.dto.update.DtoCategoryUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.model.Category;
import com.aliwert.repository.CategoryRepository;
import com.aliwert.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<DtoCategory> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoCategory getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Category").prepareErrorMessage()));
        return convertToDto(category);
    }

    @Override
    public DtoCategory createCategory(DtoCategoryInsert dtoCategoryInsert) {
        Category category = new Category();
        updateCategoryFromDto(category, dtoCategoryInsert);
        return convertToDto(categoryRepository.save(category));
    }

    @Override
    public DtoCategory updateCategory(Long id, DtoCategoryUpdate dtoCategoryUpdate) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Category").prepareErrorMessage()));
        updateCategoryFromDto(category, dtoCategoryUpdate);
        return convertToDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private DtoCategory convertToDto(Category category) {
        DtoCategory dto = new DtoCategory();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setImageUrl(category.getImageUrl());
        dto.setCreateTime((Date) category.getCreatedTime());
        dto.setTrackIds(category.getTracks().stream().map(track -> track.getId()).collect(Collectors.toList()));
        return dto;
    }

    private void updateCategoryFromDto(Category category, Object dto) {
        if (dto instanceof DtoCategoryInsert insert) {
            category.setName(insert.getName());
            category.setDescription(insert.getDescription());
            category.setImageUrl(insert.getImageUrl());
        } else if (dto instanceof DtoCategoryUpdate update) {
            category.setName(update.getName());
            category.setDescription(update.getDescription());
            category.setImageUrl(update.getImageUrl());
        }
    }
}