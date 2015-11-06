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

    private static List<String> searchBing(String query, int numberOfResults) {
        List<String> urls = new ArrayList<String>();

        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.bing.com/search?&q=" + query + "&count=" + numberOfResults + "&lf=1")
                    .header("Accept-Language", "en-US")
                    .userAgent("Mozilla")
                    .timeout(0)
                    .get();

            Elements elements = doc.select(".b_algo h2 a");

            for(int i = 0; i < elements.size() && i < numberOfResults; i++) {
                String url = elements.get(i).attr("href");
                urls.add(url);
            }
        } catch (IOException e) {
            System.err.println("[SEARCHENGINE] " + e.getMessage());
        }
        return urls;
    }

    private static List<String> searchGoogle(String query, int numberOfResults) {
        List<String> urls = new ArrayList<String>();

        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.google.com/search?&q=" + query)
                    .header("Accept-Language", "en-US")
                    .userAgent("Mozilla")
                    .timeout(0)
                    .get();

            Elements elements = doc.select(".g>h3 a");

            for(int i = 0; i < elements.size() && i < numberOfResults; i++) {
                String url = elements.get(i).attr("href");
                if(url.startsWith("/url"))
                    urls.add("http://google.com" + url);
            }
        } catch (IOException e) {
            System.err.println("[SEARCHENGINE] " + e.getMessage());
        }
        return urls;
    }

    private static List<String> searchYahoo(String query, int numberOfResults) {
        List<String> urls = new ArrayList<String>();

        Document doc = null;
        try {
            doc = Jsoup.connect("http://search.yahoo.com/search?p=" + query + "&n=" + numberOfResults)
                    .header("Accept-Language", "en-US")
                    .userAgent("Mozilla")
                    .timeout(0)
                    .get();

            Elements elements = doc.select(".compTitle a");

            for(int i = 0; i < elements.size() && i < numberOfResults; i++) {
                String url = elements.get(i).attr("href");
                urls.add(url);
            }
        } catch (IOException e) {
            System.err.println("[SEARCHENGINE] " + e.getMessage());
        }
        return urls;
    }

    public static List<String> search(String searchEngine, String query, int numberOfResults) {
        searchEngine = searchEngine.toUpperCase();

        switch(searchEngine) {
            case "GOOGLE":
                    return searchGoogle(query, numberOfResults);

            case "YAHOO":
                    return searchYahoo(query, numberOfResults);

            case "BING":
            default:
                return searchBing(query, numberOfResults);
        }
    }
}
