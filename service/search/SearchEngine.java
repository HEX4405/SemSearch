package service.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchEngine {

    private SearchEngine(){

    }

    public static List<String> search(String query, int numberOfResults) {
        List<String> urls = new ArrayList<String>();

        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.bing.com/search?&q=" + query + "&count=" + numberOfResults + "&lf=1")
                    .header("Accept-Language", "en-US")
                    .timeout(0)
                    .get();

            Elements elements = doc.select(".b_algo h2 a");

            for(Element e : elements) {
                String url = e.attr("href");
                urls.add(url);
            }
        } catch (IOException e) {
            System.err.println("[SEARCHENGINE] " + e.getMessage());
        }
        return urls;
    }
}
