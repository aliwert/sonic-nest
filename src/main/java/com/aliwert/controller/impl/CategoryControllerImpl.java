package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.ICategoryController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoCategory;
import com.aliwert.dto.insert.DtoCategoryInsert;
import com.aliwert.dto.update.DtoCategoryUpdate;
import com.aliwert.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryControllerImpl extends BaseController implements ICategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoCategory>> getAllCategories() {
        return ok(categoryService.getAllCategories());
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoCategory> getCategoryById(@PathVariable Long id) {
        return ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoCategory> createCategory(@RequestBody DtoCategoryInsert dtoCategoryInsert) {
        return ok(categoryService.createCategory(dtoCategoryInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoCategory> updateCategory(@PathVariable Long id, @RequestBody DtoCategoryUpdate dtoCategoryUpdate) {
        return ok(categoryService.updateCategory(id, dtoCategoryUpdate));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return null;
    }
}