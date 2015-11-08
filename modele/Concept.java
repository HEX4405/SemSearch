package modele;

import java.awt.Image;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
        	this.imageLink = Concept.getFinalURL(imageLink);
        	this.imageLink = URLDecoder.decode(this.getImageLink());
        	System.out.println("LOOOL"+" "+this.imageLink);
            URL url = new URL(this.imageLink);
            image = ImageIO.read(url.openStream());
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
    
    private static String getFinalURL(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setInstanceFollowRedirects(false);
        con.connect();
        con.getInputStream();

        if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM || con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
            String redirectUrl = con.getHeaderField("Location");
            return getFinalURL(redirectUrl);
        }
        return url;
    }
}
