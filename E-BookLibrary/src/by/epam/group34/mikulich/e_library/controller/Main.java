package by.epam.group34.mikulich.e_library.controller;

import by.epam.group34.mikulich.e_library.entity.Book;
import by.epam.group34.mikulich.e_library.entity.Genres;
import by.epam.group34.mikulich.e_library.entity.Roles;
import by.epam.group34.mikulich.e_library.entity.User;
import by.epam.group34.mikulich.e_library.service.BookServiceImp;
import by.epam.group34.mikulich.e_library.service.UserServiceImp;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.LSOutput;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String searchValue;
        int userChoice;
       List<Book> books = new ArrayList<>();
        List<Book>userListBooks = new ArrayList<>();
        System.out.println("Добро пожаловать в библиотеку!");
        boolean isUserExist = true;
       User user = null;

        while (isUserExist) {
            System.out.print("Введите логин: ");
            String login = scan.next();
            //System.out.println(login);
            System.out.print("Введите пароль: ");
            String pass = scan.next();
           // System.out.println(pass);
            user = UserServiceImp.userAuthentification(login, pass);
            if (user == null) {
                System.out.println("Неправильно введен логин или пароль. Повторите ввод");
            }
            else {
                isUserExist = false;

                System.out.println(user.getFirstName() + " добро пожаловать в библиотеку");
            }
        }

        boolean menuChoice = true;

        while (menuChoice) {
            System.out.println("Выберите действие в меню:");
            System.out.println("1. Найти книгу \n2. Взять книгу \n3. Сдать книгу");
            System.out.println("4. Добавить книгу \n5. Удалить книгу \n6. Зарегистрировать нового пользователя");
            System.out.println("7. Выйти из библиотеки");
            System.out.println("Сделайте Ваш выбор:");
            userChoice = scan.nextInt();

            int i =0;
            switch (userChoice) {
                // Поиск книги.
                case (1):

                    System.out.println("Введите название или автора книги");
                    searchValue  = scan.next();
                   books = BookServiceImp.booksSearching(searchValue);
                    System.out.println("Результаты поиска");
                    if (books.isEmpty()) {
                        System.out.println("Поиск результатов не дал. Попробуйте еще раз");
                        break;}
                    printBookList(books);
                    System.out.println("Желаете взять книгу из поиска?");
                    System.out.println("1.Да\n2.Нет");
                    userChoice =scan.nextInt();
                    if (userChoice ==2){
                    break;}
                    i=1;
                //Взятие книги пользователем.
                case (2):// BookServiceImp.takeBook(user);
                       if (i!=1) {
                           books=BookServiceImp.bookList();
                       }
                    if (books.isEmpty()) {
                        System.out.println("В библиотеки нету книг");
                        break;}
                    System.out.println("Выберите нужную Вам книгу ");
                    printBookList(books);
                    System.out.println("Укажите номер книги: ");
                    userChoice = scan.nextInt() - 1;
                    int isBookadd = UserServiceImp.addBook(user, books.get(userChoice));
                    if (isBookadd==1) {
                        System.out.println("Вы взяли " +
                                books.get(userChoice).getAuthor() +" "+
                                books.get(userChoice).getTitle()+". Приятного чтения!");
                    }
                   else {
                        System.out.println("Пользователь или книга отсутствует");
                        break;}
                    break;

                    //Сдать книгу
                case (3):
                        List<Book> booksOnHands = new ArrayList<>();
                        booksOnHands = UserServiceImp.booksOnHands(user);
                    if (booksOnHands==null) {
                        System.out.println("У Вас нету книг");
                        break;
                    }
                        printBookList(booksOnHands);
                        System.out.println("Введите номер книги которую желаете сдать:");
                        boolean bookNumberCheck = true;
                        while (bookNumberCheck) {
                            bookNumberCheck = false;
                            userChoice = scan.nextInt();
                            int deleteBookCheck;
                            deleteBookCheck = UserServiceImp.removeBookFromUser(user, userChoice, booksOnHands.size());
                            if (deleteBookCheck == -1) {
                                System.out.println("У Вас нет книги под таким номером");
                                bookNumberCheck = true;
                            }
                        }
                        System.out.println("Книга сдана. Спасибо!");
                    break;

                    // Добавление книги
                case(4):
                    if(user.getRole().equals(Roles.USER)) {
                        System.out.println("Данное меню доступно только пользователям с правом ADMIN");
                        break;
                    }
                    String newBookTitle, newBookAuthor;
                    int newBookPage;
                    int id;
                    Genres newBookGenre;
                    System.out.println("Введите название книги:");
                    newBookTitle=scan.next();
                    System.out.println("Введите автора книги:");
                    newBookAuthor = scan.next();
                    System.out.println("Введите количество страниц:");
                    newBookPage=scan.nextInt();
                    System.out.println("Выберите жанр книги из предложенных:");
                    Genres genres[] = Genres.values();
                    for (Genres c : genres){
                        System.out.println(c);
                    }
                    System.out.println("Введите жанр книги");
                    newBookGenre=Genres.valueOf(scan.next().toUpperCase(Locale.ROOT));
                    //System.out.println(newBookGenre);
                    int addCheck =BookServiceImp.addNewBook(newBookTitle,newBookAuthor,newBookPage,newBookGenre);
                    if (addCheck ==1) {
                        System.out.println("Книга сспешно добавлена");
                        break;
                    }
                    else {System.out.println();
                        break;}

                    //удаление книги
                case(5):
                    if(user.getRole().equals(Roles.USER)) {
                        System.out.println("Данное меню доступно только пользователям с правом ADMIN");
                        break;
                    }
                   // books = BookServiceImp.bookList();
                    if (books.isEmpty()) {
                        System.out.println("В библиотеки нету книг");
                        break;}
                    System.out.println("Выберите книгу для удаления");
                    printBookList(books);
                    System.out.println("Укажите номер книги: ");
                    userChoice = scan.nextInt() - 1;
                    int deleteBook;
                    deleteBook = BookServiceImp.removeBook(userChoice, books.size());
                    if (deleteBook==1){
                        System.out.println("Книга удалена");
                    }
                    break;

                case(6):
                    if(user.getRole().equals(Roles.USER)) {
                        System.out.println("Данное меню доступно только пользователям с правом ADMIN");
                        break;
                    }
                    System.out.println("Выберите пользователя для удаления");


                    case(7):
                    menuChoice = false;
                    System.out.println("Досвидание!");
                    break;

            }

            }
        }

    public static void printBookList(List<Book> books){
       for( int i =0; i<books.size(); i++) {
           System.out.println((i+1)+"."+books.get(i).toString());
       }
       }





    }


