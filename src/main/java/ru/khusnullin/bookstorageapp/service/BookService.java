package ru.khusnullin.bookstorageapp.service;

import ru.khusnullin.bookstorageapp.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getBooks();
    BookDto addBook(BookDto bookDto);
}
