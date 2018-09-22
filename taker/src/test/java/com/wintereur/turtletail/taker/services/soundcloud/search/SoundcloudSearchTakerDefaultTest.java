package com.wintereur.turtletail.taker.services.soundcloud.search;

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.channel.ChannelInfoItem;
import com.wintereur.turtletail.taker.services.soundcloud.SoundcloudSearchTaker;
import com.wintereur.turtletail.taker.services.youtube.takers.YoutubeSearchTaker;
import com.wintereur.turtletail.taker.stream.StreamInfoItem;

import static org.junit.Assert.*;
import static com.wintereur.turtletail.taker.ServiceList.SoundCloud;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;

/*
 * Created by Christian Schabesberger on 27.05.18
 *
 * Copyright (C) Christian Schabesberger 2018 <chris.schabesberger@mailbox.com>
 * YoutubeSearchTakerStreamTest.java is part of TurtleTail.
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
 * Test for {@link YoutubeSearchTaker}
 */
public class SoundcloudSearchTakerDefaultTest extends SoundcloudSearchTakerBaseTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        TurtleTail.init(Downloader.getInstance());
        taker = (SoundcloudSearchTaker) SoundCloud.getSearchTaker("lill uzi vert", "de");
        taker.fetchPage();
        itemsPage = taker.getInitialPage();
    }

    @Test
    public void testGetSecondPageUrl() throws Exception {
        assertEquals("https://api-v2.soundcloud.com/search?q=lill+uzi+vert&limit=10&offset=10",
                removeClientId(taker.getNextPageUrl()));
    }

    @Test
    public void testResultList_FirstElement() {
        InfoItem firstInfoItem = itemsPage.getItems().get(0);

        // THe channel should be the first item
        assertEquals("name", "Bad and Boujee (Feat. Lil Uzi Vert) [Prod. By Metro Boomin]", firstInfoItem.getName());
        assertEquals("url","https://soundcloud.com/migosatl/bad-and-boujee-feat-lil-uzi-vert-prod-by-metro-boomin", firstInfoItem.getUrl());
    }

    @Test
    public void testResultListCheckIfContainsStreamItems() {
        boolean hasStreams = false;
        for(InfoItem item : itemsPage.getItems()) {
            if(item instanceof StreamInfoItem) {
                hasStreams = true;
            }
        }
        assertTrue("Has no InfoItemStreams", hasStreams);
    }

    @Test
    public void testGetSecondPage() throws Exception {
        SoundcloudSearchTaker secondTaker = (SoundcloudSearchTaker) SoundCloud.getSearchTaker("lill uzi vert", "de");
        ListTaker.InfoItemsPage<InfoItem> secondPage = secondTaker.getPage(itemsPage.getNextPageUrl());
        assertTrue(Integer.toString(secondPage.getItems().size()),
                secondPage.getItems().size() >= 10);

        // check if its the same result
        boolean equals = true;
        for (int i = 0; i < secondPage.getItems().size()
                && i < itemsPage.getItems().size(); i++) {
            if(!secondPage.getItems().get(i).getUrl().equals(
                    itemsPage.getItems().get(i).getUrl())) {
                equals = false;
            }
        }
        assertFalse("First and second page are equal", equals);

        assertEquals("https://api-v2.soundcloud.com/search?q=lill+uzi+vert&limit=10&offset=20",
                removeClientId(secondPage.getNextPageUrl()));
    }


    @Test
    public void testId() throws Exception {
        assertEquals("lill uzi vert", taker.getId());
    }

    @Test
    public void testName() {
        assertEquals("lill uzi vert", taker.getName());
    }
}
