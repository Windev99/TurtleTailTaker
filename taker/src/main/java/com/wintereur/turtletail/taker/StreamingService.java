package com.wintereur.turtletail.taker;

import com.wintereur.turtletail.taker.channel.ChannelTaker;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.kiosk.KioskList;
import com.wintereur.turtletail.taker.playlist.PlaylistTaker;
import com.wintereur.turtletail.taker.search.SearchTaker;
import com.wintereur.turtletail.taker.linkhandler.*;
import com.wintereur.turtletail.taker.stream.StreamTaker;
import com.wintereur.turtletail.taker.subscription.SubscriptionTaker;

import java.util.Collections;
import java.util.List;

public abstract class StreamingService {
    public static class ServiceInfo {
        private final String name;
        private final List<MediaCapability> mediaCapabilities;

        public ServiceInfo(String name, List<MediaCapability> mediaCapabilities) {
            this.name = name;
            this.mediaCapabilities = Collections.unmodifiableList(mediaCapabilities);
        }

        public String getName() {
            return name;
        }

        public List<MediaCapability> getMediaCapabilities() {
            return mediaCapabilities;
        }

        public enum MediaCapability {
            AUDIO, VIDEO, LIVE
        }
    }

    public enum LinkType {
        NONE,
        STREAM,
        CHANNEL,
        PLAYLIST
    }

    private final int serviceId;
    private final ServiceInfo serviceInfo;

    public StreamingService(int id, String name, List<ServiceInfo.MediaCapability> capabilities) {
        this.serviceId = id;
        this.serviceInfo = new ServiceInfo(name, capabilities);
    }

    public final int getServiceId() {
        return serviceId;
    }

    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    @Override
    public String toString() {
        return serviceId + ":" + serviceInfo.getName();
    }

    ////////////////////////////////////////////
    // Url Id handler
    ////////////////////////////////////////////
    public abstract LinkHandlerFactory getStreamLHFactory();
    public abstract ListLinkHandlerFactory getChannelLHFactory();
    public abstract ListLinkHandlerFactory getPlaylistLHFactory();
    public abstract SearchQueryHandlerFactory getSearchQHFactory();


    ////////////////////////////////////////////
    // Taker
    ////////////////////////////////////////////
    public abstract SearchTaker getSearchTaker(SearchQueryHandler queryHandler, String contentCountry);
    public abstract SuggestionTaker getSuggestionTaker();
    public abstract SubscriptionTaker getSubscriptionTaker();
    public abstract KioskList getKioskList() throws ExtractionException;

    public abstract ChannelTaker getChannelTaker(ListLinkHandler urlIdHandler) throws ExtractionException;
    public abstract PlaylistTaker getPlaylistTaker(ListLinkHandler urlIdHandler) throws ExtractionException;
    public abstract StreamTaker getStreamTaker(LinkHandler UIHFactory) throws ExtractionException;

    public SearchTaker getSearchTaker(String query, List<String> contentFilter, String sortFilter, String contentCountry) throws ExtractionException {
        return getSearchTaker(getSearchQHFactory().fromQuery(query, contentFilter, sortFilter), contentCountry);
    }

    public ChannelTaker getChannelTaker(String id, List<String> contentFilter, String sortFilter) throws ExtractionException {
        return getChannelTaker(getChannelLHFactory().fromQuery(id, contentFilter, sortFilter));
    }

    public PlaylistTaker getPlaylistTaker(String id, List<String> contentFilter, String sortFilter) throws ExtractionException {
        return getPlaylistTaker(getPlaylistLHFactory().fromQuery(id, contentFilter, sortFilter));
    }

    public SearchTaker getSearchTaker(String query, String contentCountry) throws ExtractionException {
        return getSearchTaker(getSearchQHFactory().fromQuery(query), contentCountry);
    }

    public ChannelTaker getChannelTaker(String url) throws ExtractionException {
        return getChannelTaker(getChannelLHFactory().fromUrl(url));
    }

    public PlaylistTaker getPlaylistTaker(String url) throws ExtractionException {
        return getPlaylistTaker(getPlaylistLHFactory().fromUrl(url));
    }

    public StreamTaker getStreamTaker(String url) throws ExtractionException {
        return getStreamTaker(getStreamLHFactory().fromUrl(url));
    }



    /**
     * figure out where the link is pointing to (a channel, video, playlist, etc.)
     */
    public final LinkType getLinkTypeByUrl(String url) throws ParsingException {
        LinkHandlerFactory sH = getStreamLHFactory();
        LinkHandlerFactory cH = getChannelLHFactory();
        LinkHandlerFactory pH = getPlaylistLHFactory();

        if (sH.acceptUrl(url)) {
            return LinkType.STREAM;
        } else if (cH.acceptUrl(url)) {
            return LinkType.CHANNEL;
        } else if (pH.acceptUrl(url)) {
            return LinkType.PLAYLIST;
        } else {
            return LinkType.NONE;
        }
    }
}
