package service.extract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Extractor {

    private List<String> texts;

    public Extractor()
    {
        texts = new ArrayList<String>();
    }

    public void addUrl(String url) throws IOException {
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
            String text = doc.select("p").text();
            texts.add(text);
    }

    public void addUrls(List<String> urls) throws IOException {
        for(String url : urls) {
            this.addUrl(url);
        }
    }

    public List<String> getTexts()
    {
        return texts;
    }

}
