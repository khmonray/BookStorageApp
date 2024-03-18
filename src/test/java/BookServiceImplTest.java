import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.khusnullin.bookstorageapp.dto.BookDto;
import ru.khusnullin.bookstorageapp.entity.Book;
import ru.khusnullin.bookstorageapp.mapper.BookMapper;
import ru.khusnullin.bookstorageapp.repository.BookRepository;
import ru.khusnullin.bookstorageapp.service.BookService;
import ru.khusnullin.bookstorageapp.service.BookServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    private BookService bookService;

    @Before
    public void setUp() {
        bookService = new BookServiceImpl(bookRepository, bookMapper);
    }

    @Test
    public void testGetBooksShouldReturnEmptyList() {
        List<Book> emptyList = Collections.emptyList();
        Mockito.when(bookRepository.findAll()).thenReturn(emptyList);

        List<BookDto> books = bookService.getBooks();

        assertEquals(Collections.emptyList(), books);
    }

    @Test
    public void testGetBooksShouldReturnMappedDtos() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", "description", null));
        books.add(new Book(2, "Book 2", "description", null));

        List<BookDto> mappedDtos = Arrays.asList(bookMapper.mapBookToDto(books.get(0)),
                bookMapper.mapBookToDto(books.get(1)));

        Mockito.when(bookRepository.findAll()).thenReturn(books);
        Mockito.when(bookMapper.mapBookToDto(Mockito.any(Book.class))).thenReturn(mappedDtos.get(0), mappedDtos.get(1));

        List<BookDto> actualBooks = bookService.getBooks();

        assertEquals(mappedDtos, actualBooks);
    }

    @Test
    public void testAddBookWithValidJson() throws IOException {
        String bookJson = "{\"id\": 1, \"title\": \"Book 1\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        BookDto bookDto = objectMapper.readValue(bookJson, BookDto.class);
        Book book = bookMapper.mapBookDtoToBook(bookDto);

        Mockito.doNothing().when(bookRepository).save(book);

        bookService.addBook(bookJson);

        Mockito.verify(bookRepository).save(book);
    }

    @Test
    public void testGetBookByIdWithExistingBook() {
        int id = 1;
        Book book = new Book(id, "Book 1", "description", null);

        Mockito.when(bookRepository.findById(id)).thenReturn(book);
        Mockito.when(bookMapper.mapBookToDto(book)).thenReturn(new BookDto());

        BookDto bookDto = bookService.getBookById(id);

        assertNotNull(bookDto);
    }

    @Test
    public void testDeleteBook() {
        int id = 1;

        Mockito.doNothing().when(bookRepository).delete(id);

        bookService.deleteBook(id);

        Mockito.verify(bookRepository).delete(id);
    }


}
