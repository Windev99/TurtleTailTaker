package com.wintereur.turtletail.taker.services.soundcloud.search;

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.channel.ChannelInfoItem;
import com.wintereur.turtletail.taker.services.soundcloud.SoundcloudSearchTaker;
import com.wintereur.turtletail.taker.services.soundcloud.SoundcloudSearchQueryHandlerFactory;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static com.wintereur.turtletail.taker.ServiceList.SoundCloud;

public class SoundcloudSearchTakerChannelOnlyTest extends SoundcloudSearchTakerBaseTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        TurtleTail.init(Downloader.getInstance());
        taker = (SoundcloudSearchTaker) SoundCloud.getSearchTaker("lill uzi vert",
                asList(SoundcloudSearchQueryHandlerFactory.USERS), null, "de");
        taker.fetchPage();
        itemsPage = taker.getInitialPage();
    }

    @Test
    public void testGetSecondPage() throws Exception {
        SoundcloudSearchTaker secondTaker = (SoundcloudSearchTaker) SoundCloud.getSearchTaker("lill uzi vert",
                asList(SoundcloudSearchQueryHandlerFactory.USERS), null, "de");
        ListTaker.InfoItemsPage<InfoItem> secondPage = secondTaker.getPage(itemsPage.getNextPageUrl());
        assertTrue(Integer.toString(secondPage.getItems().size()),
                secondPage.getItems().size() >= 3);

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

        assertEquals("https://api-v2.soundcloud.com/search/users?q=lill+uzi+vert&limit=10&offset=20",
                removeClientId(secondPage.getNextPageUrl()));
    }

    @Test
    public void testGetSecondPageUrl() throws Exception {
        assertEquals("https://api-v2.soundcloud.com/search/users?q=lill+uzi+vert&limit=10&offset=10",
                removeClientId(taker.getNextPageUrl()));
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
