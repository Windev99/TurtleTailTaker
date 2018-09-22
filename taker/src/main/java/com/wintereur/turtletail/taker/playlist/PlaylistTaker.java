package com.wintereur.turtletail.taker.playlist;

import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.StreamingService;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.linkhandler.ListLinkHandler;
import com.wintereur.turtletail.taker.stream.StreamInfoItem;

public abstract class PlaylistTaker extends ListTaker<StreamInfoItem> {

    public PlaylistTaker(StreamingService service, ListLinkHandler urlIdHandler) {
        super(service, urlIdHandler);
    }

    public abstract String getThumbnailUrl() throws ParsingException;
    public abstract String getBannerUrl() throws ParsingException;

    public abstract String getUploaderUrl() throws ParsingException;
    public abstract String getUploaderName() throws ParsingException;
    public abstract String getUploaderAvatarUrl() throws ParsingException;

    public abstract long getStreamCount() throws ParsingException;
}
