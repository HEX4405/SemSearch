package service.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchEngine {

    public enum Engine {
        GOOGLE,
        BING,
        YAHOO
    }

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

            for(Element e : elements) {
                String url = e.attr("href");
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

            for(Element e : elements) {
                String url = e.attr("href");
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

            for(Element e : elements) {
                String url = e.attr("href");
                urls.add(url);
            }
        } catch (IOException e) {
            System.err.println("[SEARCHENGINE] " + e.getMessage());
        }
        return urls;
    }

    public static List<String> search(Engine engine, String query, int numberOfResults) {
        switch(engine) {
            case BING:
                    return searchBing(query, numberOfResults);

            case GOOGLE:
                    return searchGoogle(query, numberOfResults);

            case YAHOO:
                    return searchYahoo(query, numberOfResults);

            default:
                    return null;
        }
    }
}
