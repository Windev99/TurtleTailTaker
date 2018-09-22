package com.wintereur.turtletail.taker.services.youtube;

import com.wintereur.turtletail.taker.*;
import com.wintereur.turtletail.taker.linkhandler.*;
import com.wintereur.turtletail.taker.channel.ChannelTaker;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.kiosk.KioskTaker;
import com.wintereur.turtletail.taker.kiosk.KioskList;
import com.wintereur.turtletail.taker.playlist.PlaylistTaker;
import com.wintereur.turtletail.taker.search.SearchTaker;
import com.wintereur.turtletail.taker.services.youtube.takers.*;
import com.wintereur.turtletail.taker.services.youtube.linkHandler.*;
import com.wintereur.turtletail.taker.stream.StreamTaker;
import com.wintereur.turtletail.taker.subscription.SubscriptionTaker;

import static java.util.Arrays.asList;
import static com.wintereur.turtletail.taker.StreamingService.ServiceInfo.MediaCapability.*;


/*
 * Created by Christian Schabesberger on 23.08.15.
 *
 * Copyright (C) Christian Schabesberger 2018 <chris.schabesberger@mailbox.com>
 * YoutubeService.java is part of TurtleTail.
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

public class YoutubeService extends StreamingService {

    public YoutubeService(int id) {
        super(id, "YouTube", asList(AUDIO, VIDEO, LIVE));
    }

    @Override
    public SearchTaker getSearchTaker(SearchQueryHandler query, String contentCountry) {
        return new YoutubeSearchTaker(this, query, contentCountry);
    }

    @Override
    public LinkHandlerFactory getStreamLHFactory() {
        return YoutubeStreamLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getChannelLHFactory() {
        return YoutubeChannelLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return YoutubePlaylistLinkHandlerFactory.getInstance();
    }

    @Override
    public SearchQueryHandlerFactory getSearchQHFactory() {
        return YoutubeSearchQueryHandlerFactory.getInstance();
    }

    @Override
    public StreamTaker getStreamTaker(LinkHandler linkHandler) throws ExtractionException {
        return new YoutubeStreamTaker(this, linkHandler);
    }

    @Override
    public ChannelTaker getChannelTaker(ListLinkHandler urlIdHandler) throws ExtractionException {
        return new YoutubeChannelTaker(this, urlIdHandler);
    }

    @Override
    public PlaylistTaker getPlaylistTaker(ListLinkHandler urlIdHandler) throws ExtractionException {
        return new YoutubePlaylistTaker(this, urlIdHandler);
    }

    @Override
    public SuggestionTaker getSuggestionTaker() {
        return new YoutubeSuggestionTaker(getServiceId());
    }

    @Override
    public KioskList getKioskList() throws ExtractionException {
        KioskList list = new KioskList(getServiceId());

        // add kiosks here e.g.:
        try {
            list.addKioskEntry(new KioskList.KioskTakerFactory() {
                @Override
                public KioskTaker createNewKiosk(StreamingService streamingService, String url, String id)
                throws ExtractionException {
                    return new YoutubeTrendingTaker(YoutubeService.this,
                            new YoutubeTrendingLinkHandlerFactory().fromUrl(url), id);
                }
            }, new YoutubeTrendingLinkHandlerFactory(), "Trending");
            list.setDefaultKiosk("Trending");
        } catch (Exception e) {
            throw new ExtractionException(e);
        }

        return list;
    }

    @Override
    public SubscriptionTaker getSubscriptionTaker() {
        return new YoutubeSubscriptionTaker(this);
    }

}
