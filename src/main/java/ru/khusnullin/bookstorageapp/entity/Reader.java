package ru.khusnullin.bookstorageapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Reader {
    private int id;
    private String name;
    private List<Book> books;
}
