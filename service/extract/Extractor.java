package service.extract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Extractor {

    private Extractor()
    {

    }

    public static List<String> extract(List<String> urls) throws IOException {
        List<String> texts = new ArrayList<>();

        for(String url : urls) {
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
            String text = doc.select("p").text();
            texts.add(text);
        }

        return texts;
    }
}
