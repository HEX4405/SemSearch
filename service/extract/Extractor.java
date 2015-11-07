package service.extract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Extractor {

    private Extractor() {

    }

    public static Map<String, String> extract(List<String> urls) {
        Map<String, String> textsMap = new HashMap<>();

        for(String url : urls) {
            try {
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla")
                        .followRedirects(true)
                        .timeout(60*1000)
                        .get();

                String title = doc.select("title").text();
                String text = doc.select("p").text();
                if(!text.isEmpty()) {
                    textsMap.put(title, text);
                }
            } catch(IOException e) {
                System.err.println("[EXTRACTOR] " + e.getMessage());
            }
        }

        return textsMap;
    }
}
