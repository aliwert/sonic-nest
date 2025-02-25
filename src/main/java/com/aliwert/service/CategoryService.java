package com.aliwert.service;

import com.aliwert.dto.DtoCategory;
import com.aliwert.dto.insert.DtoCategoryInsert;
import com.aliwert.dto.update.DtoCategoryUpdate;
import java.util.List;

public interface CategoryService {
    List<DtoCategory> getAllCategories();
    DtoCategory getCategoryById(Long id);
    DtoCategory createCategory(DtoCategoryInsert dtoCategoryInsert);
    DtoCategory updateCategory(Long id, DtoCategoryUpdate dtoCategoryUpdate);
    void deleteCategory(Long id);
}