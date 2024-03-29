package ru.khusnullin.bookstorageapp.repository;

import ru.khusnullin.bookstorageapp.config.DatabaseConnection;
import ru.khusnullin.bookstorageapp.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository implements CommonRepository<Book> {

    ReaderRepository readerRepository = new ReaderRepository();

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection == null) {
                throw new SQLException("Connection could not be established");
            }
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setReader(readerRepository.findById(resultSet.getInt("reader_id")));
                books.add(book);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public Book findById(int id) {
        Book book = null;
        String query = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setReader(readerRepository.findById(resultSet.getInt("reader_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public void save(Book book) {
        String query = "INSERT INTO books (title, reader_id) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            if (book.getReader() != null) {
                statement.setInt(2, book.getReader().getId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM books WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book book) {
        String query = "UPDATE books SET title = ?, reader_id = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            if (book.getReader() != null) {
                statement.setInt(2, book.getReader().getId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.setInt(3, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
