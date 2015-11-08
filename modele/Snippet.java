package modele;


import java.util.ArrayList;
import java.util.List;

public class Snippet {

	private static List<Concept> listDefinitions= new ArrayList<>();
    private String title;
    private Concept mainConcept;
    private List<Concept> associatedConcepts;

    public Snippet(String title, Concept mainConcept, List<Concept> associatedConcepts) {
        this.title = title;
        this.mainConcept = mainConcept;
        this.associatedConcepts = associatedConcepts;
    }

    
    public static List<Concept> getListDefinitions() {
        return listDefinitions;
    }
    
    public static void addDefinition(Concept concept){
    	Snippet.listDefinitions.add(concept);
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
