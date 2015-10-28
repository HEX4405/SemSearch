package service.finder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class URIFinder {

    private URIFinder() {

    }

    public static List<List<String>> find(List<String> texts) {
        List<List<String>> urisLists = new ArrayList<>();

        Connection spotlight = Jsoup.connect("http://spotlight.sztaki.hu:2222/rest/annotate")
                .header("content-type", "application/x-www-form-urlencoded")
                .data("confidence", "0.5")
                .data("support", "0")
                //.data("spotter", "Default")
                //.data("disambiguator", "Default")
                //.data("policy", "whitelist")
                .timeout(0);

        for(String text : texts) {
            List<String> uris = new ArrayList<>();
            String encodedText = null;
            try {
                encodedText = URLEncoder.encode(text, "UTF-8");
                Document doc = spotlight.data("text", encodedText).post();

                for (Element e : doc.select("a")) {
                    uris.add(e.attr("href"));
                }

                urisLists.add(uris);
            } catch (UnsupportedEncodingException e) {
                System.err.println(e.getMessage());
            } catch(IOException e) {
                System.err.println(e.getMessage());
            }
        }

        return urisLists;
    }
}
