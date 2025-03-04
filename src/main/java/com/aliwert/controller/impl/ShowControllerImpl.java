package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IShowController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoShow;
import com.aliwert.dto.insert.DtoShowInsert;
import com.aliwert.dto.update.DtoShowUpdate;
import com.aliwert.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@RequiredArgsConstructor
public class ShowControllerImpl extends BaseController implements IShowController {

    private final ShowService showService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoShow>> getAllShows() {
        return ok(showService.getAllShows());
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoShow> getShowById(@PathVariable Long id) {
        return ok(showService.getShowById(id));
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoShow> createShow(@RequestBody DtoShowInsert dtoShowInsert) {
        return ok(showService.createShow(dtoShowInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoShow> updateShow(@PathVariable Long id, @RequestBody DtoShowUpdate dtoShowUpdate) {
        return ok(showService.updateShow(id, dtoShowUpdate));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deleteShow(Long id) {
        showService.deleteShow(id);
        return null;
    }
}