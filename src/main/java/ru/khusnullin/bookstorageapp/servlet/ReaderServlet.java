package ru.khusnullin.bookstorageapp.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.khusnullin.bookstorageapp.dto.ReaderDto;
import ru.khusnullin.bookstorageapp.mapper.ReaderMapper;
import ru.khusnullin.bookstorageapp.repository.ReaderRepository;
import ru.khusnullin.bookstorageapp.service.ReaderService;
import ru.khusnullin.bookstorageapp.service.ReaderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "readerServlet", value = "/readers")
public class ReaderServlet extends HttpServlet {
    private final ReaderService readerService;

    public ReaderServlet() {
        this.readerService = new ReaderServiceImpl(new ReaderRepository(), new ReaderMapper());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String idParam = req.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            ReaderDto readerDto = readerService.getReader(id);

            if (readerDto != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonBook = objectMapper.writeValueAsString(readerDto);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(jsonBook);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            List<ReaderDto> readers = readerService.getReaders();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonReaders = objectMapper.writeValueAsString(readers);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsonReaders);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String json = sb.toString();

        readerService.addReader(json);

        resp.setStatus(HttpServletResponse.SC_CREATED);


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String readerId = req.getParameter("id");

        if (readerId == null || readerId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Reader ID is required");
            return;
        }
        try {
            int id = Integer.parseInt(readerId);
            readerService.deleteReader(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting reader");
        }
    }
}
