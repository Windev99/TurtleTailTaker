package com.wintereur.turtletail.taker.services.youtube.takers;

import org.jsoup.nodes.Element;
import com.wintereur.turtletail.taker.channel.ChannelInfoItemTaker;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.utils.Utils;

/*
 * Created by Christian Schabesberger on 12.02.17.
 *
 * Copyright (C) Christian Schabesberger 2017 <chris.schabesberger@mailbox.com>
 * YoutubeChannelInfoItemTaker.java is part of TurtleTail.
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

public class YoutubeChannelInfoItemTaker implements ChannelInfoItemTaker {
    private final Element el;

    public YoutubeChannelInfoItemTaker(Element el) {
        this.el = el;
    }

    @Override
    public String getThumbnailUrl() throws ParsingException {
        Element img = el.select("span[class*=\"yt-thumb-simple\"]").first()
                .select("img").first();

        String url = img.attr("abs:src");

        if (url.contains("gif")) {
            url = img.attr("abs:data-thumb");
        }
        return url;
    }

    @Override
    public String getName() throws ParsingException {
        return el.select("a[class*=\"yt-uix-tile-link\"]").first()
                .text();
    }

    @Override
    public String getUrl() throws ParsingException {
        return el.select("a[class*=\"yt-uix-tile-link\"]").first()
                .attr("abs:href");
    }

    @Override
    public long getSubscriberCount() throws ParsingException {
        final Element subsEl = el.select("span[class*=\"yt-subscriber-count\"]").first();
        if (subsEl != null) {
            try {
                return Long.parseLong(Utils.removeNonDigitCharacters(subsEl.text()));
            } catch (NumberFormatException e) {
                throw new ParsingException("Could not get subscriber count", e);
            }
        } else {
            // If the element is null, the channel have the subscriber count disabled
            return -1;
        }
    }

    @Override
    public long getStreamCount() throws ParsingException {
        Element metaEl = el.select("ul[class*=\"yt-lockup-meta-info\"]").first();
        if (metaEl == null) {
            return 0;
        } else {
            return Long.parseLong(Utils.removeNonDigitCharacters(metaEl.text()));
        }
    }

    @Override
    public String getDescription() throws ParsingException {
        Element desEl = el.select("div[class*=\"yt-lockup-description\"]").first();
        if (desEl == null) {
            return "";
        } else {
            return desEl.text();
        }
    }
}
