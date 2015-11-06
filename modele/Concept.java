package modele;

public class Concept {

    private String title;
    private String description;


    public Concept(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
}
