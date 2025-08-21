package com.aliwert.service.impl;

import com.aliwert.dto.DtoCategory;
import com.aliwert.dto.insert.DtoCategoryInsert;
import com.aliwert.dto.update.DtoCategoryUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.CategoryMapper;
import com.aliwert.model.Category;
import com.aliwert.repository.CategoryRepository;
import com.aliwert.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DtoCategory> getAllCategories() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public DtoCategory getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Category").prepareErrorMessage()));
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public DtoCategory createCategory(DtoCategoryInsert dtoCategoryInsert) {
        Category category = categoryMapper.toEntity(dtoCategoryInsert);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public DtoCategory updateCategory(Long id, DtoCategoryUpdate dtoCategoryUpdate) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Category").prepareErrorMessage()));
        categoryMapper.updateEntityFromDto(dtoCategoryUpdate, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Category").prepareErrorMessage()));
        categoryRepository.delete(category);
    }
}