package ru.khusnullin.bookstorageapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.khusnullin.bookstorageapp.dto.ReaderDto;
import ru.khusnullin.bookstorageapp.entity.Book;
import ru.khusnullin.bookstorageapp.entity.Reader;
import ru.khusnullin.bookstorageapp.mapper.ReaderMapper;
import ru.khusnullin.bookstorageapp.repository.BookRepository;
import ru.khusnullin.bookstorageapp.repository.ReaderRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;
    private final BookRepository bookRepository;

    public ReaderServiceImpl(ReaderRepository readerRepository, ReaderMapper readerMapper) {
        this.readerRepository = readerRepository;
        this.readerMapper = readerMapper;
        this.bookRepository = new BookRepository();
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
            try {
                throw new IllegalArgumentException("Reader not found");
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
        }
        return readerMapper.mapReaderToDto(reader);
    }

    public void assignBookToReader(int readerId, int bookId) {
        Reader reader = readerRepository.findById(readerId);
        if (reader == null) {
            throw new IllegalArgumentException("Reader not found with id: " + readerId);
        }

        Book book = bookRepository.findById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book not found with id: " + bookId);
        }

        if (reader.getBooks() != null && !reader.getBooks().contains(book)) {
            reader.getBooks().add(book);
        } else if (reader.getBooks() == null) {
            reader.setBooks(new ArrayList<>());
        }
        book.setReader(reader);
        reader.getBooks().add(book);
        bookRepository.update(book);
        readerRepository.update(reader);
    }
}
