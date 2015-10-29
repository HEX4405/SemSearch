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

    public static List<String> find(String text) {
        List<String> uris = new ArrayList<>();

        try {
            Document doc = Jsoup.connect("http://spotlight.sztaki.hu:2222/rest/annotate")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .data("confidence", "0.2")
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

    public static List<List<String>> find(List<String> texts) {
        List<List<String>> urisLists = new ArrayList<>();

        for(String text : texts) {
            urisLists.add(find(text));
        }

        return urisLists;
    }
}
