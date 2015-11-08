package modele;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Concept {

    private String title;
    private String description;
    private String imageLink;
    private Image image;


    public Concept(String title, String description, String imageLink) {
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
        this.image = null;
        /*try {
            URL url = new URL(this.imageLink);
            image = ImageIO.read(url);
        } catch (IOException e) {
        	e.printStackTrace();
        }*/
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
    
    public Image getImage(){
    	return this.image;
    }
}
