package com.wintereur.turtletail.taker.services.youtube.search;

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.channel.ChannelInfoItem;
import com.wintereur.turtletail.taker.services.youtube.takers.YoutubeSearchTaker;
import com.wintereur.turtletail.taker.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;

public class YoutubeSearchTakerChannelOnlyTest extends YoutubeSearchTakerBaseTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        TurtleTail.init(Downloader.getInstance());
        taker = (YoutubeSearchTaker) YouTube.getSearchTaker("pewdiepie",
                asList(YoutubeSearchQueryHandlerFactory.CHANNELS), null, "de");
        taker.fetchPage();
        itemsPage = taker.getInitialPage();
    }

    @Test
    public void testGetSecondPage() throws Exception {
        YoutubeSearchTaker secondTaker = (YoutubeSearchTaker) YouTube.getSearchTaker("pewdiepie",
                asList(YoutubeSearchQueryHandlerFactory.CHANNELS), null, "de");
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

        assertEquals("https://www.youtube.com/results?q=pewdiepie&sp=EgIQAlAU&page=3", secondPage.getNextPageUrl());
    }

    @Test
    public void testGetSecondPageUrl() throws Exception {
        assertEquals("https://www.youtube.com/results?q=pewdiepie&sp=EgIQAlAU&page=2", taker.getNextPageUrl());
    }

    @Test
    public void testOnlyContainChannels() {
        for(InfoItem item : itemsPage.getItems()) {
            if(!(item instanceof ChannelInfoItem)) {
                fail("The following item is no channel item: " + item.toString());
            }
        }
    }
}
