package com.wintereur.turtletail.taker.services.soundcloud;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import com.wintereur.turtletail.taker.Downloader;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.SuggestionTaker;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SoundcloudSuggestionTaker extends SuggestionTaker {

    public static final String CHARSET_UTF_8 = "UTF-8";

    public SoundcloudSuggestionTaker(int serviceId) {
        super(serviceId);
    }

    @Override
    public List<String> suggestionList(String query, String contentCountry) throws IOException, ExtractionException {
        List<String> suggestions = new ArrayList<>();

        Downloader dl = TurtleTail.getDownloader();

        String url = "https://api-v2.soundcloud.com/search/queries"
                + "?q=" + URLEncoder.encode(query, CHARSET_UTF_8)
                + "&client_id=" + SoundcloudParsingHelper.clientId()
                + "&limit=10";

        String response = dl.download(url);
        try {
            JsonArray collection = JsonParser.object().from(response).getArray("collection");
            for (Object suggestion : collection) {
                if (suggestion instanceof JsonObject) suggestions.add(((JsonObject) suggestion).getString("query"));
            }

            return suggestions;
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse json response", e);
        }
    }
}
