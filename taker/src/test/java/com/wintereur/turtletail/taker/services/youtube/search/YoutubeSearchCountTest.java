package com.wintereur.turtletail.taker.services.youtube.search;

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.channel.ChannelInfoItem;
import com.wintereur.turtletail.taker.services.youtube.takers.YoutubeSearchTaker;
import com.wintereur.turtletail.taker.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory;

import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertTrue;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;

public class YoutubeSearchCountTest {
    public static class YoutubeChannelViewCountTest extends YoutubeSearchTakerBaseTest {
        @BeforeClass
        public static void setUpClass() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (YoutubeSearchTaker) YouTube.getSearchTaker("pewdiepie",
                    singletonList(YoutubeSearchQueryHandlerFactory.CHANNELS), null,"de");
            taker.fetchPage();
            itemsPage = taker.getInitialPage();
        }

        @Test
        public void testViewCount() throws Exception {
            boolean foundKnownChannel = false;
            ChannelInfoItem ci = (ChannelInfoItem) itemsPage.getItems().get(0);
            assertTrue("Count does not fit: " + Long.toString(ci.getSubscriberCount()),
                    65043316 < ci.getSubscriberCount() && ci.getSubscriberCount() < 68043316);
        }
    }
}
