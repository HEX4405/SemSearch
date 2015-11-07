package modele;

public class Concept {

    private String title;
    private String description;
    private String imageLink;


    public Concept(String title, String description, String imageLink) {
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
    
    public String getImageLink() {
        return this.imageLink;
    }
}
