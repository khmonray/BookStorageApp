package ru.khusnullin.bookstorageapp.mapper;

import ru.khusnullin.bookstorageapp.dto.BookDto;
import ru.khusnullin.bookstorageapp.dto.ReaderDto;
import ru.khusnullin.bookstorageapp.entity.Book;
import ru.khusnullin.bookstorageapp.entity.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReaderMapper {
    public ReaderDto mapReaderToDto(Reader reader) {
        ReaderDto readerDto = new ReaderDto();
        readerDto.setId(reader.getId());
        readerDto.setName(reader.getName());

        List<Book> books = reader.getBooks();
        if (books == null) {
            books = new ArrayList<>();
        }

        List<BookDto> bookDtoList = books.stream()
                .filter(Objects::nonNull)
                .map(book -> new BookDto(book.getId(), book.getTitle(), book.getReader().getId()))
                .collect(Collectors.toList());
        readerDto.setBookDtoList(bookDtoList);

        return readerDto;
    }


    public Reader mapDtoToReader(ReaderDto readerDto) {
        Reader reader = new Reader();
        reader.setId(readerDto.getId());
        reader.setName(readerDto.getName());

        List<BookDto> bookDtoList = readerDto.getBookDtoList();
        if (bookDtoList == null) {
            bookDtoList = new ArrayList<>();
        }

        reader.setBooks(bookDtoList.stream()
                .filter(Objects::nonNull)
                .map(bookDto -> new Book(bookDto.getId(), bookDto.getTitle(), "", new Reader(bookDto.getReaderId(), "", new ArrayList<>())))
                .collect(Collectors.toList()));
        return reader;
    }

}

