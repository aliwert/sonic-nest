package com.aliwert.service.impl;

import com.aliwert.dto.DtoShow;
import com.aliwert.dto.insert.DtoShowInsert;
import com.aliwert.dto.update.DtoShowUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.ShowMapper;
import com.aliwert.model.Show;
import com.aliwert.repository.ShowRepository;
import com.aliwert.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final ShowMapper showMapper;

    @Override
    public List<DtoShow> getAllShows() {
        return showMapper.toDtoList(showRepository.findAll());
    }

    @Override
    public DtoShow getShowById(Long id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Show").prepareErrorMessage()));
        return showMapper.toDto(show);
    }

    @Override
    public DtoShow createShow(DtoShowInsert dtoShowInsert) {
        Show show = showMapper.toEntity(dtoShowInsert);
        return showMapper.toDto(showRepository.save(show));
    }

    @Override
    public DtoShow updateShow(Long id, DtoShowUpdate dtoShowUpdate) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Show").prepareErrorMessage()));
        showMapper.updateEntityFromDto(dtoShowUpdate, show);
        return showMapper.toDto(showRepository.save(show));
    }

    @Override
    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }
}