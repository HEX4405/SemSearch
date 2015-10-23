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

    public static List<String> search(String query, int numberOfResults) throws IOException {
        List<String> urls = new ArrayList<String>();
        Document doc = Jsoup.connect("https://www.bing.com/search?q=" + query + "&count=" + numberOfResults).get();
        Elements elements = doc.select(".b_algo h2 a");

        for(Element e : elements) {
            String url = e.attr("href");
            urls.add(url);
        }
        return urls;
    }
}
