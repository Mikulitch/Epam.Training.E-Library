package by.epam.group34.mikulich.e_library.service;

import by.epam.group34.mikulich.e_library.dao.BookDAOImp;
import by.epam.group34.mikulich.e_library.dao.UserDAOImp;
import by.epam.group34.mikulich.e_library.entity.Book;
import by.epam.group34.mikulich.e_library.entity.Genres;
import by.epam.group34.mikulich.e_library.entity.User;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImp implements BookService{
    static List<Book> books=new ArrayList<>();

    static BookDAOImp bookDaoImp = new BookDAOImp();

    public static List<Book> bookList () {
      if (!books.isEmpty()){
          books.clear();
      }
     books = bookDaoImp.allBooks();
     return books;
    }

   public static List<Book> booksSearching (String searchValue) {
       List<Book> searchBooks = new ArrayList<>();
       books = bookDaoImp.allBooks();
        for (int i = 0;i<books.size(); i++) {
           if(books.get(i).getAuthor().contains(searchValue)
            || books.get(i).getTitle().contains(searchValue)){
                searchBooks.add(books.get(i));
            }
        }
        return searchBooks;
   }

        public static int addNewBook(String title, String author, int pages, Genres genre) throws Exception {
        //сделать валидацию входящих данных
            int bookAddCheck =-1;
           if(bookDaoImp.addNewBookDAO(title,author,pages,genre))
               bookAddCheck =1;


        return bookAddCheck;
        }

        public static int removeBook (int userChoice, int totalBook ) throws Exception {
            if(userChoice>totalBook || userChoice<0 ) {
                return -1;
            }
            bookDaoImp.delete(userChoice);
        return 1;
        }





}
