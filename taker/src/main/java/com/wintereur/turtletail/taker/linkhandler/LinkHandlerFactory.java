package com.wintereur.turtletail.taker.linkhandler;

import com.wintereur.turtletail.taker.exceptions.FoundAdException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;

/*
 * Created by Christian Schabesberger on 26.07.16.
 *
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.com>
 * LinkHandlerFactory.java is part of TurtleTail.
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

public abstract class LinkHandlerFactory {

    ///////////////////////////////////
    // To Override
    ///////////////////////////////////

    public abstract String getId(String url) throws ParsingException;
    public abstract String getUrl(String id) throws ParsingException;
    public abstract boolean onAcceptUrl(final String url) throws ParsingException;

    ///////////////////////////////////
    // Logic
    ///////////////////////////////////

    public LinkHandler fromUrl(String url) throws ParsingException {
        if(url == null) throw new IllegalArgumentException("url can not be null");
        if(!acceptUrl(url)) {
            throw new ParsingException("Malformed unacceptable url: " + url);
        }

        final String id = getId(url);
        return new LinkHandler(url, getUrl(id), id);
    }

    public LinkHandler fromId(String id) throws ParsingException {
        if(id == null) throw new IllegalArgumentException("id can not be null");
        final String url = getUrl(id);
        return new LinkHandler(url, url, id);
    }

    /**
     * When a VIEW_ACTION is caught this function will test if the url delivered within the calling
     * Intent was meant to be watched with this Service.
     * Return false if this service shall not allow to be called through ACTIONs.
     */
    public boolean acceptUrl(final String url) throws ParsingException {
        try {
            return onAcceptUrl(url);
        } catch (FoundAdException fe) {
            throw fe;
        }
    }
}
