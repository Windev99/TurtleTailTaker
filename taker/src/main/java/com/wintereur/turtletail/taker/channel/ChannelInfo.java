package com.wintereur.turtletail.taker.channel;

import com.wintereur.turtletail.taker.ListTaker.InfoItemsPage;
import com.wintereur.turtletail.taker.ListInfo;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.StreamingService;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.stream.StreamInfoItem;
import com.wintereur.turtletail.taker.linkhandler.ListLinkHandler;
import com.wintereur.turtletail.taker.utils.TakerHelper;

import java.io.IOException;

/*
 * Created by Christian Schabesberger on 31.07.16.
 *
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.com>
 * ChannelInfo.java is part of TurtleTail.
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

public class ChannelInfo extends ListInfo<StreamInfoItem> {

    public ChannelInfo(int serviceId, ListLinkHandler urlIdHandler, String name) throws ParsingException {
        super(serviceId, urlIdHandler, name);
    }

    public static ChannelInfo getInfo(String url) throws IOException, ExtractionException {
        return getInfo(TurtleTail.getServiceByUrl(url), url);
    }

    public static ChannelInfo getInfo(StreamingService service, String url) throws IOException, ExtractionException {
        ChannelTaker taker = service.getChannelTaker(url);
        taker.fetchPage();
        return getInfo(taker);
    }

    public static InfoItemsPage<StreamInfoItem> getMoreItems(StreamingService service, String url, String pageUrl) throws IOException, ExtractionException {
        return service.getChannelTaker(url).getPage(pageUrl);
    }

    public static ChannelInfo getInfo(ChannelTaker taker) throws IOException, ExtractionException {

        ChannelInfo info = new ChannelInfo(taker.getServiceId(),
                taker.getUIHandler(),
                taker.getName());


        try {
            info.setAvatarUrl(taker.getAvatarUrl());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setBannerUrl(taker.getBannerUrl());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setFeedUrl(taker.getFeedUrl());
        } catch (Exception e) {
            info.addError(e);
        }

        final InfoItemsPage<StreamInfoItem> itemsPage = TakerHelper.getItemsPageOrLogError(info, taker);
        info.setRelatedItems(itemsPage.getItems());
        info.setNextPageUrl(itemsPage.getNextPageUrl());

        try {
            info.setSubscriberCount(taker.getSubscriberCount());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setDescription(taker.getDescription());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setDonationLinks(taker.getDonationLinks());
        } catch (Exception e) {
            info.addError(e);
        }

        return info;
    }

    private String avatarUrl;
    private String bannerUrl;
    private String feedUrl;
    private long subscriberCount = -1;
    private String description;
    private String[] donationLinks;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public long getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(long subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getDonationLinks() {
        return donationLinks;
    }

    public void setDonationLinks(String[] donationLinks) {
        this.donationLinks = donationLinks;
    }
}
