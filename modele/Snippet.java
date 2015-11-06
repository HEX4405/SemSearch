package modele;


import java.util.ArrayList;
import java.util.List;

public class Snippet {

    private String title;
    private Concept mainConcept;
    private List<Concept> associatedConcepts;

    public Snippet(String title, Concept mainConcept, List<Concept> associatedConcepts) {
        this.title = title;
        this.mainConcept = mainConcept;
        this.associatedConcepts = new ArrayList<>();
    }

    public String getTitle() {
        return this.title;
    }

    public Concept getMainConcept() {
        return mainConcept;
    }

    public List<Concept> getAssociatedConcepts() {
        return associatedConcepts;
    }

}
