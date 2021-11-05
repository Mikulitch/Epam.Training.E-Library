package by.epam.group34.mikulich.e_library.entity;

public class Book {
    private int id;
    private String title;
    private String author;
    private Genres genre;
    private int pages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Book() {
    }

    @Override
    public String toString() {
        return "Название книги: " + title +", Автор: " + author + ", Жанр: " + genre;
    }
}
