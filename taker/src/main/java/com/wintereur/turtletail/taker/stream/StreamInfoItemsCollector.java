package com.wintereur.turtletail.taker.stream;

import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.InfoItemsCollector;
import com.wintereur.turtletail.taker.exceptions.FoundAdException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;

import java.util.List;
import java.util.Vector;

/*
 * Created by Christian Schabesberger on 28.02.16.
 *
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.com>
 * StreamInfoItemsCollector.java is part of TurtleTail.
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

public class StreamInfoItemsCollector extends InfoItemsCollector<StreamInfoItem, StreamInfoItemTaker> {

    public StreamInfoItemsCollector(int serviceId) {
        super(serviceId);
    }

    @Override
    public StreamInfoItem extract(StreamInfoItemTaker taker) throws ParsingException {
        if (taker.isAd()) {
            throw new FoundAdException("Found ad");
        }

        // important information
        int serviceId = getServiceId();
        String url = taker.getUrl();
        String name = taker.getName();
        StreamType streamType = taker.getStreamType();

        StreamInfoItem resultItem = new StreamInfoItem(serviceId, url, name, streamType);


        // optional information
        try {
            resultItem.setDuration(taker.getDuration());
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.setUploaderName(taker.getUploaderName());
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.setUploadDate(taker.getUploadDate());
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.setViewCount(taker.getViewCount());
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.setThumbnailUrl(taker.getThumbnailUrl());
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.setUploaderUrl(taker.getUploaderUrl());
        } catch (Exception e) {
            addError(e);
        }
        return resultItem;
    }

    @Override
    public void commit(StreamInfoItemTaker taker) {
        try {
            addItem(extract(taker));
        } catch (FoundAdException ae) {
            //System.out.println("AD_WARNING: " + ae.getMessage());
        } catch (Exception e) {
            addError(e);
        }
    }

    public List<StreamInfoItem> getStreamInfoItemList() {
        List<StreamInfoItem> siiList = new Vector<>();
        for(InfoItem ii : super.getItems()) {
            if(ii instanceof StreamInfoItem) {
                siiList.add((StreamInfoItem) ii);
            }
        }
        return siiList;
    }
}
