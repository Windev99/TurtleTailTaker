package com.wintereur.turtletail.taker.services.youtube.linkHandler;

import com.wintereur.turtletail.taker.linkhandler.ListLinkHandlerFactory;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.utils.Parser;

import java.util.List;

/*
 * Created by Christian Schabesberger on 25.07.16.
 *
 * Copyright (C) Christian Schabesberger 2018 <chrźis.schabesberger@mailbox.com>
 * YoutubeChannelLinkHandlerFactory.java is part of TurtleTail.
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

public class YoutubeChannelLinkHandlerFactory extends ListLinkHandlerFactory {

    private static final YoutubeChannelLinkHandlerFactory instance = new YoutubeChannelLinkHandlerFactory();
    private static final String ID_PATTERN = "/(user/[A-Za-z0-9_-]*|channel/[A-Za-z0-9_-]*)";

    public static YoutubeChannelLinkHandlerFactory getInstance() {
        return instance;
    }

    @Override
    public String getId(String url) throws ParsingException {
        return Parser.matchGroup1(ID_PATTERN, url);
    }

    @Override
    public String getUrl(String id, List<String> contentFilters, String searchFilter) {
        return "https://www.youtube.com/" + id;
    }

    @Override
    public boolean onAcceptUrl(String url) {
        return (url.contains("youtube") || url.contains("youtu.be") || url.contains("hooktube.com"))
                && (url.contains("/user/") || url.contains("/channel/"));
    }
}
