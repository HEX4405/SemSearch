package service.finder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class URIFinder {

    private URIFinder() {

    }

    public static List<String> find(String text, double confidence) {
        List<String> uris = new ArrayList<>();

        try {
            Document doc = Jsoup.connect("http://spotlight.dbpedia.org/rest/annotate")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .data("confidence", Double.toString(confidence))
                    .data("support", "0")
                    .timeout(0)
                    .data("text", text)
                    .post();

            for (Element e : doc.select("a")) {
                uris.add(e.attr("href"));
            }
        } catch(IOException e) {
            System.err.println("[URIFINDER] " + e.getMessage());
        }

        return uris;
    }

    public static List<List<String>> find(List<String> texts, double confidence) {
        List<List<String>> urisList = new ArrayList<>();

        for(String text : texts) {
            List<String> uris = find(text, confidence);
            urisList.add(find(text, confidence));
        }

        return urisList;
    }
}
