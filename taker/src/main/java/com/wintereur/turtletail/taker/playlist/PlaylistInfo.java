package com.wintereur.turtletail.taker.playlist;

import com.wintereur.turtletail.taker.ListTaker.InfoItemsPage;
import com.wintereur.turtletail.taker.ListInfo;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.StreamingService;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.linkhandler.ListLinkHandler;
import com.wintereur.turtletail.taker.stream.StreamInfoItem;
import com.wintereur.turtletail.taker.utils.TakerHelper;

import java.io.IOException;

public class PlaylistInfo extends ListInfo<StreamInfoItem> {

    private PlaylistInfo(int serviceId, ListLinkHandler urlIdHandler, String name) throws ParsingException {
        super(serviceId, urlIdHandler, name);
    }

    public static PlaylistInfo getInfo(String url) throws IOException, ExtractionException {
        return getInfo(TurtleTail.getServiceByUrl(url), url);
    }

    public static PlaylistInfo getInfo(StreamingService service, String url) throws IOException, ExtractionException {
        PlaylistTaker taker = service.getPlaylistTaker(url);
        taker.fetchPage();
        return getInfo(taker);
    }

    public static InfoItemsPage<StreamInfoItem> getMoreItems(StreamingService service, String url, String pageUrl) throws IOException, ExtractionException {
        return service.getPlaylistTaker(url).getPage(pageUrl);
    }

    /**
     * Get PlaylistInfo from PlaylistTaker
     *
     * @param taker an taker where fetchPage() was already got called on.
     */
    public static PlaylistInfo getInfo(PlaylistTaker taker) throws ExtractionException {

        final PlaylistInfo info = new PlaylistInfo(
                taker.getServiceId(),
                taker.getUIHandler(),
                taker.getName());

        try {
            info.setStreamCount(taker.getStreamCount());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setThumbnailUrl(taker.getThumbnailUrl());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setUploaderUrl(taker.getUploaderUrl());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setUploaderName(taker.getUploaderName());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setUploaderAvatarUrl(taker.getUploaderAvatarUrl());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setBannerUrl(taker.getBannerUrl());
        } catch (Exception e) {
            info.addError(e);
        }

        final InfoItemsPage<StreamInfoItem> itemsPage = TakerHelper.getItemsPageOrLogError(info, taker);
        info.setRelatedItems(itemsPage.getItems());
        info.setNextPageUrl(itemsPage.getNextPageUrl());

        return info;
    }

    private String thumbnailUrl;
    private String bannerUrl;
    private String uploaderUrl;
    private String uploaderName;
    private String uploaderAvatarUrl;
    private long streamCount = 0;

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getUploaderUrl() {
        return uploaderUrl;
    }

    public void setUploaderUrl(String uploaderUrl) {
        this.uploaderUrl = uploaderUrl;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public String getUploaderAvatarUrl() {
        return uploaderAvatarUrl;
    }

    public void setUploaderAvatarUrl(String uploaderAvatarUrl) {
        this.uploaderAvatarUrl = uploaderAvatarUrl;
    }

    public long getStreamCount() {
        return streamCount;
    }

    public void setStreamCount(long streamCount) {
        this.streamCount = streamCount;
    }
}
