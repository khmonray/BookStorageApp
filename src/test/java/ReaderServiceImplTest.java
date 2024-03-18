import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.khusnullin.bookstorageapp.dto.ReaderDto;
import ru.khusnullin.bookstorageapp.entity.Book;
import ru.khusnullin.bookstorageapp.entity.Reader;
import ru.khusnullin.bookstorageapp.mapper.ReaderMapper;
import ru.khusnullin.bookstorageapp.repository.BookRepository;
import ru.khusnullin.bookstorageapp.repository.ReaderRepository;
import ru.khusnullin.bookstorageapp.service.ReaderService;
import ru.khusnullin.bookstorageapp.service.ReaderServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class ReaderServiceImplTest {

    @Mock
    private ReaderRepository readerRepository;

    @Mock
    private ReaderMapper readerMapper;

    @Mock
    private BookRepository bookRepository;

    private ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl(readerRepository, readerMapper);
    }

    @Test
    public void testGetReadersShouldReturnEmptyList() {
        List<Reader> emptyList = Collections.emptyList();
        Mockito.when(readerRepository.findAll()).thenReturn(emptyList);

        List<ReaderDto> readers = readerService.getReaders();

        assertEquals(Collections.emptyList(), readers);
    }

    @Test
    public void testGetReadersShouldReturnMappedDtos() {
        List<Reader> readers = new ArrayList<>();
        readers.add(new Reader(1, "John Doe", null));
        readers.add(new Reader(2, "Jane Doe", null));

        List<ReaderDto> mappedDtos = Arrays.asList(readerMapper.mapReaderToDto(readers.get(0)),
                readerMapper.mapReaderToDto(readers.get(1)));

        Mockito.when(readerRepository.findAll()).thenReturn(readers);
        Mockito.when(readerMapper.mapReaderToDto(Mockito.any(Reader.class))).thenReturn(mappedDtos.get(0), mappedDtos.get(1));

        List<ReaderDto> actualReaders = readerService.getReaders();

        assertEquals(mappedDtos, actualReaders);
    }

    @Test
    public void testAddReaderWithValidJson() throws IOException {
        String readerJson = "{\"id\": 1, \"name\": \"John Doe\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        ReaderDto readerDto = objectMapper.readValue(readerJson, ReaderDto.class);
        Reader reader = readerMapper.mapDtoToReader(readerDto);

        Mockito.doNothing().when(readerRepository).save(reader);

        readerService.addReader(readerJson);

        Mockito.verify(readerRepository).save(reader);
    }

    @Test
    public void testDeleteReader() {
        int id = 1;

        Mockito.doNothing().when(readerRepository).delete(id);

        readerService.deleteReader(id);

        Mockito.verify(readerRepository).delete(id);
    }

    @Test
    public void testGetReaderWithExistingReader() {
        int id = 1;
        Reader reader = new Reader(id, "John Doe", null);

        Mockito.when(readerRepository.findById(id)).thenReturn(reader);
        Mockito.when(readerMapper.mapReaderToDto(reader)).thenReturn(new ReaderDto());

        ReaderDto readerDto = readerService.getReader(id);

        assertNotNull(readerDto);
    }

    @Test(expected = RuntimeException.class)
    public void testGetReaderWithNonExistingReader() {
        int id = 1;

        Mockito.when(readerRepository.findById(id)).thenReturn(null);

        readerService.getReader(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssignBookToReaderWithNonexistentReader() {
        int readerId = 1;
        int bookId = 2;

        Mockito.when(readerRepository.findById(readerId)).thenReturn(null);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(new Book(bookId, "Book Title", "Description", null));

        readerService.assignBookToReader(readerId, bookId);
    }

    @Test
    public void testAssignBookToReaderWithNonexistentBook() {
        int readerId = 1;
        int bookId = 0;

        Mockito.when(readerRepository.findById(readerId)).thenReturn(new Reader(readerId, "John Doe", new ArrayList<>()));
        Mockito.when(bookRepository.findById(bookId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            readerService.assignBookToReader(readerId, 0);
        });
    }


}

