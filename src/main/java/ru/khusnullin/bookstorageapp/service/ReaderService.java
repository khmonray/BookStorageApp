package ru.khusnullin.bookstorageapp.service;

import ru.khusnullin.bookstorageapp.dto.ReaderDto;
import ru.khusnullin.bookstorageapp.entity.Reader;

import java.util.List;

public interface ReaderService  {
    public List<ReaderDto> getReaders();
    public void addReader(String json);

    public void deleteReader(int id);

    public ReaderDto getReader(int id);

}
