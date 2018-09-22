package com.wintereur.turtletail.taker.services.youtube.search;

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.channel.ChannelInfoItem;
import com.wintereur.turtletail.taker.services.youtube.takers.YoutubeSearchTaker;
import com.wintereur.turtletail.taker.stream.StreamInfoItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
public class YoutubeSearchTakerDefaultTest extends YoutubeSearchTakerBaseTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        TurtleTail.init(Downloader.getInstance());
        taker = (YoutubeSearchTaker) YouTube.getSearchTaker("pewdiepie", "de");
        taker.fetchPage();
        itemsPage = taker.getInitialPage();
    }



    @Test
    public void testGetSecondPageUrl() throws Exception {
        assertEquals("https://www.youtube.com/results?q=pewdiepie&page=2", taker.getNextPageUrl());
    }

    @Test
    public void testResultList_FirstElement() {
        InfoItem firstInfoItem = itemsPage.getItems().get(0);

        // THe channel should be the first item
        assertTrue(firstInfoItem instanceof ChannelInfoItem);
        assertEquals("name", "PewDiePie", firstInfoItem.getName());
        assertEquals("url","https://www.youtube.com/user/PewDiePie", firstInfoItem.getUrl());
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
        YoutubeSearchTaker secondTaker =
                (YoutubeSearchTaker) YouTube.getSearchTaker("pewdiepie", "de");
        ListTaker.InfoItemsPage<InfoItem> secondPage = secondTaker.getPage(itemsPage.getNextPageUrl());
        assertTrue(Integer.toString(secondPage.getItems().size()),
                secondPage.getItems().size() > 10);

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

        assertEquals("https://www.youtube.com/results?q=pewdiepie&page=3", secondPage.getNextPageUrl());
    }

    @Test
    public void testSuggestionNotNull() throws Exception {
        //todo write a real test
        assertTrue(taker.getSearchSuggestion() != null);
    }


    @Test
    public void testId() throws Exception {
        assertEquals("pewdiepie", taker.getId());
    }

    @Test
    public void testName() {
        assertEquals("pewdiepie", taker.getName());
    }
}
