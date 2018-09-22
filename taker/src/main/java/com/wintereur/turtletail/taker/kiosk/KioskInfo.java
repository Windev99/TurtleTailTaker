package com.wintereur.turtletail.taker.kiosk;

/*
 * Created by Christian Schabesberger on 12.08.17.
 *
 * Copyright (C) Christian Schabesberger 2017 <chris.schabesberger@mailbox.com>
 * KioskInfo.java is part of TurtleTail.
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

import com.wintereur.turtletail.taker.*;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.stream.StreamInfoItem;
import com.wintereur.turtletail.taker.linkhandler.ListLinkHandler;
import com.wintereur.turtletail.taker.utils.TakerHelper;

import java.io.IOException;

public class KioskInfo extends ListInfo<StreamInfoItem> {

    private KioskInfo(int serviceId, ListLinkHandler urlIdHandler, String name) throws ParsingException {
        super(serviceId, urlIdHandler, name);
    }

    public static ListTaker.InfoItemsPage<StreamInfoItem> getMoreItems(StreamingService service,
                                                                           String url,
                                                                           String pageUrl,
                                                                           String contentCountry) throws IOException, ExtractionException {
        KioskList kl = service.getKioskList();
        KioskTaker taker = kl.getTakerByUrl(url, pageUrl);
        taker.setContentCountry(contentCountry);
        return taker.getPage(pageUrl);
    }

    public static KioskInfo getInfo(String url,
                                    String contentCountry) throws IOException, ExtractionException {
        return getInfo(TurtleTail.getServiceByUrl(url), url, contentCountry);
    }

    public static KioskInfo getInfo(StreamingService service,
                                    String url,
                                    String contentCountry) throws IOException, ExtractionException {
        KioskList kl = service.getKioskList();
        KioskTaker taker = kl.getTakerByUrl(url, null);
        taker.setContentCountry(contentCountry);
        taker.fetchPage();
        return getInfo(taker);
    }

    /**
     * Get KioskInfo from KioskTaker
     *
     * @param taker an taker where fetchPage() was already got called on.
     */
    public static KioskInfo getInfo(KioskTaker taker) throws ExtractionException {

        final KioskInfo info = new KioskInfo(taker.getServiceId(),
                taker.getUIHandler(),
                taker.getName());

        final ListTaker.InfoItemsPage<StreamInfoItem> itemsPage = TakerHelper.getItemsPageOrLogError(info, taker);
        info.setRelatedItems(itemsPage.getItems());
        info.setNextPageUrl(itemsPage.getNextPageUrl());

        return info;
    }
}
