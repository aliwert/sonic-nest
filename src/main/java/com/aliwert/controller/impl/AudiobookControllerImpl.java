package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IAudiobookController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoAudiobook;
import com.aliwert.dto.insert.DtoAudiobookInsert;
import com.aliwert.dto.update.DtoAudiobookUpdate;
import com.aliwert.service.AudiobookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audiobooks")
@RequiredArgsConstructor
public class AudiobookControllerImpl extends BaseController implements IAudiobookController {

    private final AudiobookService audiobookService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoAudiobook>> getAllAudiobooks() {
        return ok(audiobookService.getAllAudiobooks());
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoAudiobook> getAudiobookById(@PathVariable Long id) {
        return ok(audiobookService.getAudiobookById(id));
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoAudiobook> createAudiobook(@RequestBody DtoAudiobookInsert dtoAudiobookInsert) {
        return ok(audiobookService.createAudiobook(dtoAudiobookInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoAudiobook> updateAudiobook(@PathVariable Long id, @RequestBody DtoAudiobookUpdate dtoAudiobookUpdate) {
        return ok(audiobookService.updateAudiobook(id, dtoAudiobookUpdate));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deleteAudiobook(@PathVariable Long id) {
        audiobookService.deleteAudiobook(id);
        return null;
    }
}