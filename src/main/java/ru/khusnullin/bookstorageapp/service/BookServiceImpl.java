package ru.khusnullin.bookstorageapp.service;

import ru.khusnullin.bookstorageapp.dto.BookDto;
import ru.khusnullin.bookstorageapp.entity.Book;
import ru.khusnullin.bookstorageapp.mapper.BookMapper;
import ru.khusnullin.bookstorageapp.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDto> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::mapBookToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = bookMapper.mapBookDtoToBook(bookDto);
        bookRepository.save(book);
        return bookDto;
    }

    @Override
    public BookDto getBookById(int id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            return null;
        }
        return bookMapper.mapBookToDto(book);
    }


}
