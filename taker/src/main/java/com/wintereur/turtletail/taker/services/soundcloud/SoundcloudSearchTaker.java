package com.wintereur.turtletail.taker.services.soundcloud;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import com.wintereur.turtletail.taker.*;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.search.InfoItemsSearchCollector;
import com.wintereur.turtletail.taker.search.SearchTaker;
import com.wintereur.turtletail.taker.linkhandler.SearchQueryHandler;
import com.wintereur.turtletail.taker.utils.Parser;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.wintereur.turtletail.taker.services.soundcloud.SoundcloudSearchQueryHandlerFactory.ITEMS_PER_PAGE;

public class SoundcloudSearchTaker extends SearchTaker {

    private JsonArray searchCollection;

    public SoundcloudSearchTaker(StreamingService service,
                                     SearchQueryHandler urlIdHandler,
                                     String contentCountry) {
        super(service, urlIdHandler, contentCountry);
    }

    @Override
    public String getSearchSuggestion() throws ParsingException {
        return null;
    }

    @Nonnull
    @Override
    public InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        return new InfoItemsPage<>(collectItems(searchCollection), getNextPageUrl());
    }

    @Override
    public String getNextPageUrl() throws IOException, ExtractionException {
        return getNextPageUrlFromCurrentUrl(getUrl());
    }

    @Override
    public InfoItemsPage<InfoItem> getPage(String pageUrl) throws IOException, ExtractionException {
        final Downloader dl = getDownloader();
        try {
            searchCollection = JsonParser.object().from(dl.download(pageUrl)).getArray("collection");
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse json response", e);
        }

        return new InfoItemsPage<>(collectItems(searchCollection), getNextPageUrlFromCurrentUrl(pageUrl));
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
        final Downloader dl = getDownloader();
        final String url = getUrl();
        try {
            searchCollection = JsonParser.object().from(dl.download(url)).getArray("collection");
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse json response", e);
        }

        if (searchCollection.size() == 0) {
            throw new SearchTaker.NothingFoundException("Nothing found");
        }
    }

    private InfoItemsCollector<InfoItem, InfoItemTaker> collectItems(JsonArray searchCollection) {
        final InfoItemsSearchCollector collector = getInfoItemSearchCollector();

        for (Object result : searchCollection) {
            if (!(result instanceof JsonObject)) continue;
            //noinspection ConstantConditions
            JsonObject searchResult = (JsonObject) result;
            String kind = searchResult.getString("kind", "");
            switch (kind) {
                case "user":
                    collector.commit(new SoundcloudChannelInfoItemTaker(searchResult));
                    break;
                case "track":
                    collector.commit(new SoundcloudStreamInfoItemTaker(searchResult));
                    break;
                case "playlist":
                    collector.commit(new SoundcloudPlaylistInfoItemTaker(searchResult));
                    break;
            }
        }

        return collector;
    }

    private String getNextPageUrlFromCurrentUrl(String currentUrl)
            throws MalformedURLException, UnsupportedEncodingException {
        final int pageOffset = Integer.parseInt(
                Parser.compatParseMap(
                        new URL(currentUrl)
                                .getQuery())
                        .get("offset"));

        return currentUrl.replace("&offset=" +
                        Integer.toString(pageOffset),
                "&offset=" + Integer.toString(pageOffset + ITEMS_PER_PAGE));
    }
}
