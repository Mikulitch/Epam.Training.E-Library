package by.epam.group34.mikulich.e_library.dao;

import by.epam.group34.mikulich.e_library.entity.Book;
import by.epam.group34.mikulich.e_library.entity.Genres;
import by.epam.group34.mikulich.e_library.entity.Roles;
import by.epam.group34.mikulich.e_library.entity.User;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImp implements UserDAO{
    String filepath = "UserList.xml";
    File xmlFile = new File(filepath);
    Document document;
    @Override
    public List<User> allUsers () {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<User> userList = new ArrayList<>();
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("User");

            for (int i = 0; i < nodeList.getLength(); i++) {

                userList.add(getUser(nodeList.item(i)));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return userList;
    }

    private  User getUser(Node node) throws Exception {
        User user = new User();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            user.setUserName(getTagValue("userName", element));
            user.setLastName(getTagValue("lastName", element));
            user.setFirstName(getTagValue("firstName", element));
            user.setPassword(getTagValue("password", element));
            user.setRole(Roles.valueOf(getTagValue("role", element)));


        }

        return user;
    }
    private static String getTagValue(String tag, Element element) throws Exception {

        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);

        return node.getNodeValue();
    }

    public void addBookDao(int userNumber, Book book) throws Exception {

        NodeList nodelist = document.getElementsByTagName("listBook");
        Element books = document.createElement("Book");
        Element title = document.createElement("title");
        Element author = document.createElement("author");
        Element genre = document.createElement("genre");
        title.appendChild(document.createTextNode(book.getTitle()));
        books.appendChild(title);
        author.appendChild(document.createTextNode(book.getAuthor()));
        books.appendChild(author);
        genre.appendChild(document.createTextNode(book.getGenre().toString()));
        books.appendChild(genre);
        //Node n = (Element) books;
        nodelist.item(userNumber).appendChild(books);
        transforming();
        // Преобразовать XML ==> Источник в результат

    }


    public List<Book> getBooksOnHand (int userNumber) throws Exception{
        List<Book> bookOnHand = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("listBook");
        NodeList usersBooks = nodeList.item(userNumber).getChildNodes();

        for (int i = 1; i < usersBooks.getLength(); i++) {

           bookOnHand.add(getBook(usersBooks.item(i)));
        }
        return bookOnHand;
    }

    private static Book getBook(Node node) throws Exception{
            Book book = new Book();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            book.setAuthor(getBookValue("author", element));
            book.setTitle(getBookValue("title", element));
            book.setGenre(Genres.valueOf(getBookValue("genre", element)));
           }
        return book;
    }
    private static String getBookValue(String tag, Element element) throws Exception {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();

      Node node = (Node) nodeList.item(0);

        return node.getNodeValue();
    }


    public void isBookDeleteFromUser(int userNumber, int userChoice) throws Exception{
       // NodeList booklist = document.getElementsByTagName("Book");
        Node node = (Element)document.getElementsByTagName("listBook").item(userNumber);
        Element element = (Element)node.getChildNodes().item(userChoice);
        element.getParentNode().removeChild(element);
        transforming();

    }


        public void transforming() throws Exception{
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source source = new DOMSource(document);
            Result result = new StreamResult(xmlFile);
            transformer.transform (source, result);
        }
   @Override
    public void add(User user) {
       /* user.setId(AUTO_ID.getAndIncrement());
       user.put(user.getId(), user);*/
    }

    @Override
    public void delete(User film) {
       /* user.remove(film.getId());*/
    }

    @Override
    public void edit(User film) {
       /* user.put(film.getId(), film);*/
    }

    /*@Override
    public User getById(int id) {
        return user.get(id);
    }*/
}
