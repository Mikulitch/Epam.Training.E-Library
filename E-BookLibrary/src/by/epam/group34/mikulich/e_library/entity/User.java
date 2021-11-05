package by.epam.group34.mikulich.e_library.entity;

import java.util.List;

public class User {
    private int id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private List<Book> listBook;
    private Roles role;

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public List<Book> getListBook() {
        return listBook;
    }

    public void setListBook(List<Book> listBook) {
        this.listBook = listBook;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User() {
    }

    /*public User(String userName, String password, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }*/
/* public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }*/

    @Override
    public String toString() {
        return "User: Name = " + this.firstName + " Last Name = " + this.lastName+ " " +this.listBook+ " "+this.password;
    }
}
