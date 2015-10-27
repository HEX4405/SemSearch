package service.finder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class URIFinder {

    private URIFinder() {

    }

    public static List<String> find(String text) throws IOException {
        List<String> uris = new ArrayList<>();

        Document doc = Jsoup.connect("http://spotlight.dbpedia.org/rest/annotate")
                .data("text", URLEncoder.encode(text, "UTF-8"))
                .data("confidence", "0.2")
                .data("support", "20")
                .post();

        for(Element e : doc.select("a")) {
            uris.add(e.attr("href"));
        }

        return uris;
    }
}
