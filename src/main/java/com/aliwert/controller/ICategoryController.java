package com.aliwert.controller;

import com.aliwert.dto.DtoCategory;
import com.aliwert.dto.insert.DtoCategoryInsert;
import com.aliwert.dto.update.DtoCategoryUpdate;
import java.util.List;

public interface ICategoryController {
    RootEntity<List<DtoCategory>> getAllCategories();
    RootEntity<DtoCategory> getCategoryById(Long id);
    RootEntity<DtoCategory> createCategory(DtoCategoryInsert dtoCategoryInsert);
    RootEntity<DtoCategory> updateCategory(Long id, DtoCategoryUpdate dtoCategoryUpdate);
    RootEntity<Void> deleteCategory(Long id);
}