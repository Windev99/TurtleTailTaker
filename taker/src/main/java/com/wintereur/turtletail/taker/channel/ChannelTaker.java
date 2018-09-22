package com.wintereur.turtletail.taker.channel;

import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.StreamingService;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.stream.StreamInfoItem;
import com.wintereur.turtletail.taker.linkhandler.ListLinkHandler;

/*
 * Created by Christian Schabesberger on 25.07.16.
 *
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.com>
 * ChannelTaker.java is part of TurtleTail.
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

public abstract class ChannelTaker extends ListTaker<StreamInfoItem> {

    public ChannelTaker(StreamingService service, ListLinkHandler urlIdHandler) {
        super(service, urlIdHandler);
    }

    public abstract String getAvatarUrl() throws ParsingException;
    public abstract String getBannerUrl() throws ParsingException;
    public abstract String getFeedUrl() throws ParsingException;
    public abstract long getSubscriberCount() throws ParsingException;
    public abstract String getDescription() throws ParsingException;
    public abstract String[] getDonationLinks() throws ParsingException;
}
