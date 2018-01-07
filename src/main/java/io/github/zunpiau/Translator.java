package io.github.zunpiau;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class Translator {

    @SuppressWarnings("FieldCanBeLocal")
    private final String QUERY_URL = "http://dict.youdao.com/jsonapi?xmlVersion=5.1&dicts=%7b%22count%22%3a99%2c%22dicts%22%3a%5b%5b%22ec%22%5d%5d%7d&jsonversion=2&q=";

    public Dict query(String word) throws IOException {
        String response = request (word);
        Dict dict = new Dict (word);
        JSONObject root = new JSONObject (response);
        if (!root.has ("simple"))
            return dict;
        JSONObject wordObject = root.getJSONObject ("simple")
                .getJSONArray ("word")
                .getJSONObject (0);
        Iterator<String> iterator = wordObject.keys ();
        String key;
        while (iterator.hasNext ()) {
            key = iterator.next ();
            if (key.equals ("usphone") || key.equals ("phone"))
                dict.setPhone (wordObject.getString (key));
            if (key.equals ("usspeech") || key.equals ("speech"))
                dict.setSpeech (wordObject.getString (key));
        }
        return dict;
    }

    private String request(String word) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) new URL (
                QUERY_URL + URLEncoder.encode (word, "UTF-8")).openConnection ();
        connection.setRequestMethod ("GET");
        BufferedReader bf = new BufferedReader (new InputStreamReader (connection.getInputStream ()));
        String response = bf.readLine ();
        bf.close ();
        connection.disconnect ();
        return response;
    }

}
