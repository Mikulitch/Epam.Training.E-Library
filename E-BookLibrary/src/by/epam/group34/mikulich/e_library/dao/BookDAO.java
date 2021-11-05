package by.epam.group34.mikulich.e_library.dao;

import by.epam.group34.mikulich.e_library.entity.Book;
import by.epam.group34.mikulich.e_library.entity.User;

import java.util.List;

public interface BookDAO {
    List<Book> allBooks();
    void add(Book book);
    void delete(int i) throws Exception;
    void edit(Book book);
}
