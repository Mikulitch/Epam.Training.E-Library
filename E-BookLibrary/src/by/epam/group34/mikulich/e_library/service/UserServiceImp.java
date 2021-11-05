package by.epam.group34.mikulich.e_library.service;

import by.epam.group34.mikulich.e_library.dao.UserDAOImp;
import by.epam.group34.mikulich.e_library.entity.Book;
import by.epam.group34.mikulich.e_library.entity.Roles;
import by.epam.group34.mikulich.e_library.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImp {
     static List<User> users ;
     static User rightUser;
    static UserDAOImp userDaoImp = new UserDAOImp();
    public static User userAuthentification(String login, String pass) throws  NullPointerException{

        if (isEmptyFields(login, pass) ) {
           return null;
        }

                users = userDaoImp.allUsers();

               for (int i = 0;i< users.size();i++) {
                    if (login.equals(users.get(i).getUserName()) &&
                            pass.equals(users.get(i).getPassword())                    ) {
                        rightUser=users.get(i);
                        break;
                    }
                   else rightUser=null;
                }
        return rightUser;
    }
    public static boolean isEmptyFields (String login, String pass){
        if (login ==null || pass ==null){
        return  true;}
        else
        return false;
    }

    public static int addBook (User user, Book book) throws Exception  {
        int userNumber=findUserNumber(user);

        if (user==null || book == null) {
            return 0;
        }
        userDaoImp.addBookDao(userNumber, book);
        return 1;
    }
    public static List<Book> booksOnHands(User user) throws Exception {
        int userNumber=findUserNumber(user);
        List<Book> listBooksOnHand = new ArrayList<>();
        listBooksOnHand=userDaoImp.getBooksOnHand(userNumber);
        if (listBooksOnHand.isEmpty()) {
            return null;
        }else
        return listBooksOnHand;
    }

    public static int removeBookFromUser(User user , int userChoice ,int totalBook) throws Exception{int userNumber=findUserNumber(user);
    int result;
        if(userChoice>totalBook || userChoice<0 ) {
            result = -1;
        }else {
    userDaoImp.isBookDeleteFromUser(userNumber,userChoice);
        result =1;}
    return result;
    }


    public static  int findUserNumber(User user) {
        int userNmb =-1;
        for (int i =0;i<users.size();i++){
            if (user.getUserName().equals(users.get(i).getUserName())){
                userNmb=i;
            }
        }
        return userNmb;
    }

    public static List<User> takeAllUsers () throws Exception {
        List<User> userList = new ArrayList<>();
        userList =userDaoImp.allUsersList();
        //валидация
        return userList;
    }

    public static int addNewUser(String newUserName,
                                 String newUserPassword,
                                 String newUserFirstName,
                                 String newUserLastName,
                                 List<Book>newUserListBook,
                                 Roles newUserRole ) throws Exception {
        int userAddCheck =-1;
        if(userDaoImp.addNewUserDAO(newUserName,newUserPassword,newUserFirstName,
                newUserLastName,newUserListBook,newUserRole))
            userAddCheck =1;




        return userAddCheck;
    }

    public static int deleteUser(int userChoice) throws Exception {

        userDaoImp.deleteUserDAO(userChoice-1);
        return 1;
    }

 }
