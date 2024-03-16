package ru.khusnullin.bookstorageapp.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "bookServlet", value = "/books")
public class BookServlet extends HttpServlet {
}
