package com.wintereur.turtletail.taker.search;

import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.InfoItemTaker;
import com.wintereur.turtletail.taker.InfoItemsCollector;
import com.wintereur.turtletail.taker.channel.ChannelInfoItemTaker;
import com.wintereur.turtletail.taker.channel.ChannelInfoItemsCollector;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.playlist.PlaylistInfoItemTaker;
import com.wintereur.turtletail.taker.playlist.PlaylistInfoItemsCollector;
import com.wintereur.turtletail.taker.stream.StreamInfoItemTaker;
import com.wintereur.turtletail.taker.stream.StreamInfoItemsCollector;

/*
 * Created by Christian Schabesberger on 12.02.17.
 *
 * Copyright (C) Christian Schabesberger 2017 <chris.schabesberger@mailbox.com>
 * InfoItemsSearchCollector.java is part of TurtleTail.
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

/**
 * Collector for search results
 *
 * This collector can handle the following taker types:
 * <ul>
 *     <li>{@link StreamInfoItemTaker}</li>
 *     <li>{@link ChannelInfoItemTaker}</li>
 *     <li>{@link PlaylistInfoItemTaker}</li>
 * </ul>
 * Calling {@link #extract(InfoItemTaker)} or {@link #commit(Object)} with any
 * other taker type will raise an exception.
 */
public class InfoItemsSearchCollector extends InfoItemsCollector<InfoItem, InfoItemTaker> {
    private final StreamInfoItemsCollector streamCollector;
    private final ChannelInfoItemsCollector userCollector;
    private final PlaylistInfoItemsCollector playlistCollector;

    InfoItemsSearchCollector(int serviceId) {
        super(serviceId);
        streamCollector = new StreamInfoItemsCollector(serviceId);
        userCollector = new ChannelInfoItemsCollector(serviceId);
        playlistCollector = new PlaylistInfoItemsCollector(serviceId);
    }

    @Override
    public InfoItem extract(InfoItemTaker taker) throws ParsingException {
        // Use the corresponding collector for each item taker type
        if(taker instanceof StreamInfoItemTaker) {
            return streamCollector.extract((StreamInfoItemTaker) taker);
        } else if(taker instanceof ChannelInfoItemTaker) {
            return userCollector.extract((ChannelInfoItemTaker) taker);
        } else if(taker instanceof PlaylistInfoItemTaker) {
            return playlistCollector.extract((PlaylistInfoItemTaker) taker);
        } else {
            throw new IllegalArgumentException("Invalid taker type: " + taker);
        }
    }
}
