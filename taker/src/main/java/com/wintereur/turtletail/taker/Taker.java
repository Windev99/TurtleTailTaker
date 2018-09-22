package com.wintereur.turtletail.taker;

import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.linkhandler.LinkHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public abstract class Taker {
    /**
     * {@link StreamingService} currently related to this taker.<br>
     * Useful for getting other things from a service (like the url handlers for cleaning/accepting/get id from urls).
     */
    private final StreamingService service;

    private final LinkHandler uIHandler;

    @Nullable
    private boolean pageFetched = false;
    private final Downloader downloader;

    public Taker(final StreamingService service, final LinkHandler uIHandler) {
        if(service == null) throw new NullPointerException("service is null");
        if(uIHandler == null) throw new NullPointerException("LinkHandler is null");
        this.service = service;
        this.uIHandler = uIHandler;
        this.downloader = TurtleTail.getDownloader();
        if(downloader == null) throw new NullPointerException("downloader is null");
    }

    /**
     * @return The {@link LinkHandler} of the current taker object (e.g. a ChannelTaker should return a channel url handler).
     */
    @Nonnull
    public LinkHandler getUIHandler() {
        return uIHandler;
    }

    /**
     * Fetch the current page.
     * @throws IOException if the page can not be loaded
     * @throws ExtractionException if the pages content is not understood
     */
    public void fetchPage() throws IOException, ExtractionException {
        if(pageFetched) return;
        onFetchPage(downloader);
        pageFetched = true;
    }

    protected void assertPageFetched() {
        if(!pageFetched) throw new IllegalStateException("Page is not fetched. Make sure you call fetchPage()");
    }

    protected boolean isPageFetched() {
        return pageFetched;
    }

    /**
     * Fetch the current page.
     * @param downloader the download to use
     * @throws IOException if the page can not be loaded
     * @throws ExtractionException if the pages content is not understood
     */
    public abstract void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException;

    @Nonnull
    public String getId() throws ParsingException {
        return uIHandler.getId();
    }

    /**
     * Get the name
     * @return the name
     * @throws ParsingException if the name cannot be extracted
     */
    @Nonnull
    public abstract String getName() throws ParsingException;

    @Nonnull
    public String getOriginalUrl() throws ParsingException {
        return uIHandler.getOriginalUrl();
    }

    @Nonnull
    public String getUrl() throws ParsingException {
        return uIHandler.getUrl();
    }

    @Nonnull
    public StreamingService getService() {
        return service;
    }

    public int getServiceId() {
        return service.getServiceId();
    }

    public Downloader getDownloader() {
        return downloader;
    }
}
