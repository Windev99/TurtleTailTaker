package com.wintereur.turtletail.taker.services.youtube;

        /*
         * Created by Christian Schabesberger on 12.08.17.
         *
         * Copyright (C) Christian Schabesberger 2017 <chris.schabesberger@mailbox.com>
         * YoutubeTrendingKioskInfoTest.java is part of TurtleTail.
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

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.StreamingService;
import com.wintereur.turtletail.taker.linkhandler.LinkHandlerFactory;
import com.wintereur.turtletail.taker.kiosk.KioskInfo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;

/**
 * Test for {@link KioskInfo}
 */
public class YoutubeTrendingKioskInfoTest {
    static KioskInfo kioskInfo;

    @BeforeClass
    public static void setUp()
            throws Exception {
        TurtleTail.init(Downloader.getInstance());
        StreamingService service = YouTube;
        LinkHandlerFactory LinkHandlerFactory = service.getKioskList().getListLinkHandlerFactoryByType("Trending");

        kioskInfo = KioskInfo.getInfo(YouTube, LinkHandlerFactory.fromId("Trending").getUrl(), null);
    }

    @Test
    public void getStreams() {
        assertFalse(kioskInfo.getRelatedItems().isEmpty());
    }

    @Test
    public void getId() {
        assertTrue(kioskInfo.getId().equals("Trending")
                || kioskInfo.getId().equals("Trends"));
    }

    @Test
    public void getName() {
        assertFalse(kioskInfo.getName().isEmpty());
    }
}
