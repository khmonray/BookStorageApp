package ru.khusnullin.bookstorageapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.khusnullin.bookstorageapp.dto.BookDto;
import ru.khusnullin.bookstorageapp.dto.ReaderDto;
import ru.khusnullin.bookstorageapp.entity.Book;
import ru.khusnullin.bookstorageapp.entity.Reader;
import ru.khusnullin.bookstorageapp.mapper.ReaderMapper;
import ru.khusnullin.bookstorageapp.repository.ReaderRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;

    public ReaderServiceImpl(ReaderRepository readerRepository, ReaderMapper readerMapper) {
        this.readerRepository = readerRepository;
        this.readerMapper = readerMapper;
    }

    @Override
    public List<ReaderDto> getReaders() {
        List<Reader> readers = readerRepository.findAll();
        return readers.stream()
                .map(readerMapper::mapReaderToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addReader(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ReaderDto readerDto = objectMapper.readValue(json, ReaderDto.class);
            Reader reader = readerMapper.mapDtoToReader(readerDto);
            readerRepository.save(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReader(int id) {
        readerRepository.delete(id);
    }

    @Override
    public ReaderDto getReader(int id) {
        Reader reader = readerRepository.findById(id);
        if (reader == null) {
            return null;
        }
        return readerMapper.mapReaderToDto(reader);
    }

}
