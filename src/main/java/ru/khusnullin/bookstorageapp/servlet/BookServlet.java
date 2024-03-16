package ru.khusnullin.bookstorageapp.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.khusnullin.bookstorageapp.dto.BookDto;
import ru.khusnullin.bookstorageapp.mapper.BookMapper;
import ru.khusnullin.bookstorageapp.repository.BookRepository;
import ru.khusnullin.bookstorageapp.service.BookService;
import ru.khusnullin.bookstorageapp.service.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "bookServlet", value = "/books")
public class BookServlet extends HttpServlet {
    private final BookService bookService;

    public BookServlet() {
        this.bookService = new BookServiceImpl(new BookRepository(), new BookMapper());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            BookDto bookDto = bookService.getBookById(id);

            if (bookDto != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonBook = objectMapper.writeValueAsString(bookDto);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(jsonBook);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            List<BookDto> books = bookService.getBooks();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBooks = objectMapper.writeValueAsString(books);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsonBooks);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Обработка POST запросов
    }
}
