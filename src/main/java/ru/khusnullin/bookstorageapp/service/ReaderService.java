package ru.khusnullin.bookstorageapp.service;

import ru.khusnullin.bookstorageapp.dto.ReaderDto;

import java.util.List;

public interface ReaderService {
    List<ReaderDto> getReaders();

    void addReader(String json);

    void deleteReader(int id);

    ReaderDto getReader(int id);

}
