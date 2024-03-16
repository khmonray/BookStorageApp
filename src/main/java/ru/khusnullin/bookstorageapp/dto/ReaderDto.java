package ru.khusnullin.bookstorageapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDto {
    private int id;
    private String name;
    private List<BookDto> bookDtoList;

}
