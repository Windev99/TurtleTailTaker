package com.wintereur.turtletail.taker.channel;

import com.wintereur.turtletail.taker.InfoItemsCollector;
import com.wintereur.turtletail.taker.exceptions.ParsingException;

/*
 * Created by Christian Schabesberger on 12.02.17.
 *
 * Copyright (C) Christian Schabesberger 2017 <chris.schabesberger@mailbox.com>
 * ChannelInfoItemsCollector.java is part of TurtleTail.
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

public class ChannelInfoItemsCollector extends InfoItemsCollector<ChannelInfoItem, ChannelInfoItemTaker> {
    public ChannelInfoItemsCollector(int serviceId) {
        super(serviceId);
    }

    @Override
    public ChannelInfoItem extract(ChannelInfoItemTaker taker) throws ParsingException {
        // important information
        int serviceId = getServiceId();
        String name = taker.getName();
        String  url = taker.getUrl();

        ChannelInfoItem resultItem = new ChannelInfoItem(serviceId, url, name);


        // optional information
        try {
            resultItem.setSubscriberCount(taker.getSubscriberCount());
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.setStreamCount(taker.getStreamCount());
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.setThumbnailUrl(taker.getThumbnailUrl());
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.setDescription(taker.getDescription());
        } catch (Exception e) {
            addError(e);
        }
        return resultItem;
    }
}
