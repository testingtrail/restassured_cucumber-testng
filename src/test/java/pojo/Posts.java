package pojo;

public class Posts {
    private int id;
    private String title;
    private String author;

    //Constructors and Setters/Getters created with right click generate...

    //We have to create a default constructor
    // This one will be used for serialization
    Posts(){}

    public Posts(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

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
}
