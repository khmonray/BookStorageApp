package ru.khusnullin.bookstorageapp.service;

import ru.khusnullin.bookstorageapp.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getBooks();

    void addBook(String json);

    BookDto getBookById(int bookId);

    void deleteBook(int bookId);

    void update(int bookId, BookDto bookDto);
}
