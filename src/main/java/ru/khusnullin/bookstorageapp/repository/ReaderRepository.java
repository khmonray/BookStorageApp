package ru.khusnullin.bookstorageapp.repository;

import ru.khusnullin.bookstorageapp.config.DatabaseConnection;
import ru.khusnullin.bookstorageapp.entity.Book;
import ru.khusnullin.bookstorageapp.entity.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReaderRepository implements CommonRepository<Reader> {

    @Override
    public List<Reader> findAll() {
        List<Reader> readers = new ArrayList<>();
        String query = "SELECT * FROM readers";
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection == null) {
                throw new SQLException("Connection could not be established");
            }
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Reader reader = new Reader();
                reader.setId(resultSet.getInt("id"));
                reader.setName(resultSet.getString("name"));
                List<Book> books = findBooksByReaderId(resultSet.getInt("id"));
                reader.setBooks(books);
                readers.add(reader);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

        return readers;
    }

    @Override
    public Reader findById(int id) {
        Reader reader = null;
        String query = "SELECT * FROM readers WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                reader = new Reader();
                reader.setId(resultSet.getInt("id"));
                reader.setName(resultSet.getString("name"));
                List<Book> books = findBooksByReaderId(resultSet.getInt("id"));
                reader.setBooks(books);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reader;
    }

    @Override
    public void save(Reader reader) {
        String query = "INSERT INTO readers (name) VALUES (?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, reader.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM readers WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Reader reader) {
        String query = "UPDATE readers SET name = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, reader.getName());
            statement.setInt(2, reader.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Book> findBooksByReaderId(int readerId) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE reader_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, readerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setReader(new Reader(resultSet.getInt("reader_id"), "", new ArrayList<>()));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}
