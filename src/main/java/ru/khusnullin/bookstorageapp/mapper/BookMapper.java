package ru.khusnullin.bookstorageapp.mapper;

import ru.khusnullin.bookstorageapp.dto.BookDto;
import ru.khusnullin.bookstorageapp.entity.Book;

public class BookMapper {
    public BookDto mapBookToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        if (book.getReader() != null) {
            bookDto.setReaderId(book.getReader().getId());
        }
        return bookDto;
    }

    public Book mapBookDtoToBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setDescription("Default description");
        //book.setReader(bookDto.getReaderName());
        return book;
    }
}
