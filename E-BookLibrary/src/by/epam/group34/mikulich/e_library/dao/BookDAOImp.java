package by.epam.group34.mikulich.e_library.dao;

import by.epam.group34.mikulich.e_library.entity.Book;
import by.epam.group34.mikulich.e_library.entity.Genres;
import by.epam.group34.mikulich.e_library.entity.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

public class BookDAOImp implements BookDAO{
    List<Book> bookList = new ArrayList<>();
    String filepath = "BookList.xml";
    File xmlFile = new File(filepath);
    Document document;
    @Override
    public List<Book> allBooks() {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;

            try {
                builder = factory.newDocumentBuilder();
                document = builder.parse(xmlFile);
                document.getDocumentElement().normalize();
                //System.out.println("Корневой элемент: " + document.getDocumentElement().getNodeName());
                // получаем узлы с именем User
                // теперь XML полностью загружен в память
                // в виде объекта Document
                NodeList nodeList = document.getElementsByTagName("Book");
                // создадим из него список объектов Users

                for (int i = 0; i < nodeList.getLength(); i++) {
                    bookList.add(getBook(nodeList.item(i)));
                }

         /*   // печатаем в консоль информацию по каждому объекту Language
            for (User lang : userList) {
                System.out.println(lang.toString());
            }*/
            } catch (Exception exc) {
                exc.printStackTrace();
            }

            return bookList;
        }
        private static Book getBook(Node node) {
            Book book = new Book();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                book.setId(Integer.parseInt(getTagValue("id", element)));
                book.setAuthor(getTagValue("author", element));
                book.setTitle(getTagValue("title", element));
                book.setGenre(Genres.valueOf(getTagValue("genre", element)));
                book.setPages(Integer.parseInt(getTagValue("pages", element)));

            }
            return book;
        }

        private static String getTagValue(String tag, Element element) {
            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = (Node) nodeList.item(0);
            return node.getNodeValue();
        }

            public boolean addNewBookDAO(String title, String author, int pages, Genres genre) throws Exception {
              Node root = document.getDocumentElement();
                // Создаем новую книгу по элементам
                // Сама книга <Book>
                Element book = document.createElement("Book");

                Element bookId = document.createElement("id");
                bookId.setTextContent("222");
                // <Title>
                Element bookTitle = document.createElement("title");
                // Устанавливаем значение текста внутри тега
                bookTitle.setTextContent(title);
                // <Author>
                Element bookAuthor = document.createElement("author");
                bookAuthor.setTextContent(author);
                // <pages>
                Element bookPages = document.createElement("pages");
                bookPages.setTextContent(pages + "");
                // <genre>
                Element bookGenre = document.createElement("genre");
                bookGenre.setTextContent(genre.toString());

                // Добавляем внутренние элементы книги в элемент <Book>
                book.appendChild(bookId);
                book.appendChild(bookTitle);
                book.appendChild(bookAuthor);
                book.appendChild(bookPages);
                book.appendChild(bookGenre);
                // Добавляем книгу в корневой элемент
                root.appendChild(book);
                transforming();

                // Записываем XML в файл


                return true;
            }

    @Override
    public void delete(int userChoice) throws Exception{
        Node node = (Element)document.getElementsByTagName("Book").item(userChoice);
        Element element = (Element)node.getChildNodes();
        element.getParentNode().removeChild(element);
        transforming();
    }



    public void transforming() throws Exception{
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Source source = new DOMSource(document);
        Result result = new StreamResult(xmlFile);
        transformer.transform (source, result);
    }







      /*  private static int getTagIntValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
        private static Genres getTagObjectValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
*/





    @Override
    public void add(Book book){

    }





    @Override
    public void edit(Book book){}

}
