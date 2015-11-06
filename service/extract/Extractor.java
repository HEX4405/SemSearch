package service.extract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Extractor {

    private Extractor() {

    }

    public static List<String> extract(List<String> urls) {
        List<String> texts = new ArrayList<>();

        for(String url : urls) {
            try {
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla")
                        .followRedirects(true)
                        .timeout(0)
                        .get();

                String text = doc.select("p").text();
                if(!text.isEmpty()) {
                    texts.add(text);
                }
            } catch(IOException e) {
                System.err.println("[EXTRACTOR] " + e.getMessage());
            }
        }

        return texts;
    }
}
