package ru.khusnullin.bookstorageapp.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.khusnullin.bookstorageapp.config.DatabaseConnection;
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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;


@WebServlet(name = "myServlet", value = "/my-servlet")
public class MyServlet extends HttpServlet {

    private final BookService bookService;

    public MyServlet() {
        this.bookService = new BookServiceImpl(new BookRepository(), new BookMapper());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BookDto> books = bookService.getBooks();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBooks = objectMapper.writeValueAsString(books);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonBooks);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
