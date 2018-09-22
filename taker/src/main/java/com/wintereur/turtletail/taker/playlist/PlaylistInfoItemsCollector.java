package com.wintereur.turtletail.taker.playlist;

import com.wintereur.turtletail.taker.InfoItemsCollector;
import com.wintereur.turtletail.taker.exceptions.ParsingException;

public class PlaylistInfoItemsCollector extends InfoItemsCollector<PlaylistInfoItem, PlaylistInfoItemTaker> {

    public PlaylistInfoItemsCollector(int serviceId) {
        super(serviceId);
    }

    @Override
    public PlaylistInfoItem extract(PlaylistInfoItemTaker taker) throws ParsingException {

        String name = taker.getName();
        int serviceId = getServiceId();
        String url = taker.getUrl();

        PlaylistInfoItem resultItem = new PlaylistInfoItem(serviceId, url, name);

        try {
            resultItem.setUploaderName(taker.getUploaderName());
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.setThumbnailUrl(taker.getThumbnailUrl());
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.setStreamCount(taker.getStreamCount());
        } catch (Exception e) {
            addError(e);
        }
        return resultItem;
    }
}
