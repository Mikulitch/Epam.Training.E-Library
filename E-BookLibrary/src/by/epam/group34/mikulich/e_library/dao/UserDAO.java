package by.epam.group34.mikulich.e_library.dao;

import by.epam.group34.mikulich.e_library.entity.User;

import java.util.List;

public interface UserDAO {
    List<User> allUsers();
    void add(User user);
    void delete(User user);
    void edit(User user);
  //  User getByPass(String pass);
}
