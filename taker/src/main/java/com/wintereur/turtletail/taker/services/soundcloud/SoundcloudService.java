package com.wintereur.turtletail.taker.services.soundcloud;

import com.wintereur.turtletail.taker.*;
import com.wintereur.turtletail.taker.linkhandler.*;
import com.wintereur.turtletail.taker.channel.ChannelTaker;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.kiosk.KioskTaker;
import com.wintereur.turtletail.taker.kiosk.KioskList;
import com.wintereur.turtletail.taker.playlist.PlaylistTaker;
import com.wintereur.turtletail.taker.search.SearchTaker;
import com.wintereur.turtletail.taker.stream.StreamTaker;
import com.wintereur.turtletail.taker.subscription.SubscriptionTaker;

import static java.util.Collections.singletonList;
import static com.wintereur.turtletail.taker.StreamingService.ServiceInfo.MediaCapability.AUDIO;

public class SoundcloudService extends StreamingService {

    public SoundcloudService(int id) {
        super(id, "SoundCloud", singletonList(AUDIO));
    }

    @Override
    public SearchTaker getSearchTaker(SearchQueryHandler queryHandler, String contentCountry) {
        return new SoundcloudSearchTaker(this, queryHandler, contentCountry);
    }

    @Override
    public SearchQueryHandlerFactory getSearchQHFactory() {
        return new SoundcloudSearchQueryHandlerFactory();
    }

    @Override
    public LinkHandlerFactory getStreamLHFactory() {
        return SoundcloudStreamLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getChannelLHFactory() {
        return SoundcloudChannelLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return SoundcloudPlaylistLinkHandlerFactory.getInstance();
    }


    @Override
    public StreamTaker getStreamTaker(LinkHandler LinkHandler) {
        return new SoundcloudStreamTaker(this, LinkHandler);
    }

    @Override
    public ChannelTaker getChannelTaker(ListLinkHandler urlIdHandler) {
        return new SoundcloudChannelTaker(this, urlIdHandler);
    }

    @Override
    public PlaylistTaker getPlaylistTaker(ListLinkHandler urlIdHandler) {
        return new SoundcloudPlaylistTaker(this, urlIdHandler);
    }

    @Override
    public SuggestionTaker getSuggestionTaker() {
        return new SoundcloudSuggestionTaker(getServiceId());
    }

    @Override
    public KioskList getKioskList() throws ExtractionException {
        KioskList.KioskTakerFactory chartsFactory = new KioskList.KioskTakerFactory() {
            @Override
            public KioskTaker createNewKiosk(StreamingService streamingService,
                                                 String url,
                                                 String id)
                    throws ExtractionException {
                return new SoundcloudChartsTaker(SoundcloudService.this,
                        new SoundcloudChartsLinkHandlerFactory().fromUrl(url), id);
            }
        };

        KioskList list = new KioskList(getServiceId());

        // add kiosks here e.g.:
        final SoundcloudChartsLinkHandlerFactory h = new SoundcloudChartsLinkHandlerFactory();
        try {
            list.addKioskEntry(chartsFactory, h, "Top 50");
            list.addKioskEntry(chartsFactory, h, "New & hot");
        } catch (Exception e) {
            throw new ExtractionException(e);
        }

        return list;
    }


    @Override
    public SubscriptionTaker getSubscriptionTaker() {
        return new SoundcloudSubscriptionTaker(this);
    }
}
