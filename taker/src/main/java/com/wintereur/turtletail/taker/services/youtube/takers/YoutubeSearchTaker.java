package com.wintereur.turtletail.taker.services.youtube.takers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.wintereur.turtletail.taker.Downloader;
import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.StreamingService;
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

/*
 * Created by Christian Schabesberger on 22.07.2018
 *
 * Copyright (C) Christian Schabesberger 2018 <chris.schabesberger@mailbox.com>
 * YoutubeSearchTaker.java is part of TurtleTail.
 *
 * TurtleTail is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TurtleTail is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TurtleTail.  If not, see <http://www.gnu.com/licenses/>.
 */

public class YoutubeSearchTaker extends SearchTaker {

    private Document doc;

    public YoutubeSearchTaker(StreamingService service,
                                  SearchQueryHandler urlIdHandler,
                                  String contentCountry) {
        super(service, urlIdHandler, contentCountry);
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
        final String site;
        final String url = getUrl();
        final String contentCountry = getContentCountry();
        //String url = builder.build().toString();
        //if we've been passed a valid language code, append it to the URL
        if (!contentCountry.isEmpty()) {
            //assert Pattern.matches("[a-z]{2}(-([A-Z]{2}|[0-9]{1,3}))?", languageCode);
            site = downloader.download(url, contentCountry);
        } else {
            site = downloader.download(url);
        }

        doc = Jsoup.parse(site, url);
    }

    @Override
    public String getSearchSuggestion() throws ParsingException {
        final Element el = doc.select("div[class*=\"spell-correction\"]").first();
        if (el != null) {
            return el.select("a").first().text();
        } else {
            return "";
        }
    }

    @Nonnull
    @Override
    public InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        return new InfoItemsPage<>(collectItems(doc), getNextPageUrl());
    }

    @Override
    public String getNextPageUrl() throws ExtractionException {
        return getUrl() + "&page=" + Integer.toString( 2);
    }

    @Override
    public InfoItemsPage<InfoItem> getPage(String pageUrl) throws IOException, ExtractionException {
        String site = getDownloader().download(pageUrl);
        doc = Jsoup.parse(site, pageUrl);

        return new InfoItemsPage<>(collectItems(doc), getNextPageUrlFromCurrentUrl(pageUrl));
    }

    private String getNextPageUrlFromCurrentUrl(String currentUrl)
            throws MalformedURLException, UnsupportedEncodingException {
        final int pageNr = Integer.parseInt(
                Parser.compatParseMap(
                        new URL(currentUrl)
                                .getQuery())
                        .get("page"));

        return currentUrl.replace("&page=" + Integer.toString( pageNr),
                "&page=" + Integer.toString(pageNr + 1));
    }

    private InfoItemsSearchCollector collectItems(Document doc) throws NothingFoundException  {
        InfoItemsSearchCollector collector = getInfoItemSearchCollector();

        Element list = doc.select("ol[class=\"item-section\"]").first();

        for (Element item : list.children()) {
            /* First we need to determine which kind of item we are working with.
               Youtube depicts five different kinds of items on its search result page. These are
               regular videos, playlists, channels, two types of video suggestions, and a "no video
               found" item. Since we only want videos, we need to filter out all the others.
               An example for this can be seen here:
               https://www.youtube.com/results?search_query=asdf&page=1

               We already applied a filter to the url, so we don't need to care about channels and
               playlists now.
            */

            Element el;

            if ((el = item.select("div[class*=\"search-message\"]").first()) != null) {
                throw new NothingFoundException(el.text());

                // video item type
            } else if ((el = item.select("div[class*=\"yt-lockup-video\"]").first()) != null) {
                collector.commit(new YoutubeStreamInfoItemTaker(el));
            } else if ((el = item.select("div[class*=\"yt-lockup-channel\"]").first()) != null) {
                collector.commit(new YoutubeChannelInfoItemTaker(el));
            } else if ((el = item.select("div[class*=\"yt-lockup-playlist\"]").first()) != null &&
                    item.select(".yt-pl-icon-mix").isEmpty()) {
                collector.commit(new YoutubePlaylistInfoItemTaker(el));
            }
        }

        return collector;
    }
}
