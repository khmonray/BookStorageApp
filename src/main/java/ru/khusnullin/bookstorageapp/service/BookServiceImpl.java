package ru.khusnullin.bookstorageapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.khusnullin.bookstorageapp.dto.BookDto;
import ru.khusnullin.bookstorageapp.entity.Book;
import ru.khusnullin.bookstorageapp.mapper.BookMapper;
import ru.khusnullin.bookstorageapp.repository.BookRepository;
import ru.khusnullin.bookstorageapp.repository.ReaderRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ReaderRepository readerRepository;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        readerRepository = new ReaderRepository();
    }

    @Override
    public List<BookDto> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::mapBookToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addBook(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BookDto bookDto = objectMapper.readValue(json, BookDto.class);
            Book book = bookMapper.mapBookDtoToBook(bookDto);
            bookRepository.save(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BookDto getBookById(int id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            return null;
        }
        return bookMapper.mapBookToDto(book);
    }

    @Override
    public void deleteBook(int bookId) {
        bookRepository.delete(bookId);
    }

    public void update(int bookId, BookDto bookDto) {
        if (bookDto == null) {
            throw new IllegalArgumentException("BookDto cannot be null");
        }
        Book book = bookRepository.findById(bookId);
        book.setTitle(bookDto.getTitle());
        book.setReader(readerRepository.findById(bookDto.getReaderId()));
        bookRepository.update(book);
    }
}
