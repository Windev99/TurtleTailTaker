package com.wintereur.turtletail.taker.services.youtube;

/*
 * Created by Christian Schabesberger on 12.08.17.
 *
 * Copyright (C) Christian Schabesberger 2017 <chris.schabesberger@mailbox.com>
 * YoutubeTrendingTakerTest.java is part of TurtleTail.
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
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.services.youtube.takers.YoutubeTrendingTaker;
import com.wintereur.turtletail.taker.services.youtube.linkHandler.YoutubeTrendingLinkHandlerFactory;
import com.wintereur.turtletail.taker.stream.StreamInfoItem;
import com.wintereur.turtletail.taker.utils.Utils;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;
import static com.wintereur.turtletail.taker.TakerAsserts.assertEmptyErrors;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;


/**
 * Test for {@link YoutubeTrendingLinkHandlerFactory}
 */
public class YoutubeTrendingTakerTest {

    static YoutubeTrendingTaker taker;

    @BeforeClass
    public static void setUp() throws Exception {
        TurtleTail.init(Downloader.getInstance());
        taker = (YoutubeTrendingTaker) YouTube
                .getKioskList()
                .getTakerById("Trending", null);
        taker.fetchPage();
        taker.setContentCountry("de");
    }

    @Test
    public void testGetDownloader() throws Exception {
        assertNotNull(TurtleTail.getDownloader());
    }

    @Test
    public void testGetName() throws Exception {
        assertFalse(taker.getName().isEmpty());
    }

    @Test
    public void testId() throws Exception {
        assertEquals(taker.getId(), "Trending");
    }

    @Test
    public void testGetStreamsQuantity() throws Exception {
        ListTaker.InfoItemsPage<StreamInfoItem> page = taker.getInitialPage();
        Utils.printErrors(page.getErrors());
        assertTrue("no streams are received", page.getItems().size() >= 20);
    }

    @Test
    public void testGetStreamsErrors() throws Exception {
        assertEmptyErrors("errors during stream list extraction", taker.getInitialPage().getErrors());
    }

    @Test
    public void testHasMoreStreams() throws Exception {
        // Setup the streams
        taker.getInitialPage();
        assertFalse("has more streams", taker.hasNextPage());
    }

    @Test
    public void testGetNextPage() {
        assertTrue("taker has next streams", taker.getPage(taker.getNextPageUrl()) == null
                || taker.getPage(taker.getNextPageUrl()).getItems().isEmpty());
    }

    @Test
    public void testGetUrl() throws Exception {
        assertEquals(taker.getUrl(), taker.getUrl(), "https://www.youtube.com/feed/trending");
    }
}
