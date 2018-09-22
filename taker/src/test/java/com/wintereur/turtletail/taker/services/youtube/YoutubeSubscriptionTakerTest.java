package com.wintereur.turtletail.taker.services.youtube;

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.ServiceList;
import com.wintereur.turtletail.taker.linkhandler.LinkHandlerFactory;
import com.wintereur.turtletail.taker.services.youtube.takers.YoutubeSubscriptionTaker;
import com.wintereur.turtletail.taker.subscription.SubscriptionTaker;
import com.wintereur.turtletail.taker.subscription.SubscriptionItem;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for {@link YoutubeSubscriptionTaker}
 */
public class YoutubeSubscriptionTakerTest {
    private static YoutubeSubscriptionTaker subscriptionTaker;
    private static LinkHandlerFactory urlHandler;

    @BeforeClass
    public static void setupClass() {
        TurtleTail.init(Downloader.getInstance());
        subscriptionTaker = new YoutubeSubscriptionTaker(ServiceList.YouTube);
        urlHandler = ServiceList.YouTube.getChannelLHFactory();
    }

    @Test
    public void testFromInputStream() throws Exception {
        File testFile = new File("taker/src/test/resources/youtube_export_test.xml");
        if (!testFile.exists()) testFile = new File("src/test/resources/youtube_export_test.xml");

        List<SubscriptionItem> subscriptionItems = subscriptionTaker.fromInputStream(new FileInputStream(testFile));
        assertTrue("List doesn't have exactly 8 items (had " + subscriptionItems.size() + ")", subscriptionItems.size() == 8);

        for (SubscriptionItem item : subscriptionItems) {
            assertNotNull(item.getName());
            assertNotNull(item.getUrl());
            assertTrue(urlHandler.acceptUrl(item.getUrl()));
            assertFalse(item.getServiceId() == -1);
        }
    }

    @Test
    public void testEmptySourceException() throws Exception {
        String emptySource = "<opml version=\"1.1\"><body>" +
                "<outline text=\"Testing\" title=\"123\" />" +
                "</body></opml>";

        List<SubscriptionItem> items = subscriptionTaker.fromInputStream(new ByteArrayInputStream(emptySource.getBytes("UTF-8")));
        assertTrue(items.isEmpty());
    }

    @Test
    public void testInvalidSourceException() {
        List<String> invalidList = Arrays.asList(
                "<xml><notvalid></notvalid></xml>",
                "<opml><notvalid></notvalid></opml>",
                "<opml><body></body></opml>",
                "<opml><body><outline text=\"fail\" title=\"fail\" type=\"rss\" xmlUgrl=\"invalidTag\"/></outline></body></opml>",
                "<opml><body><outline><outline text=\"invalid\" title=\"url\" type=\"rss\"" +
                        " xmlUrl=\"https://www.youtube.com/feeds/videos.xml?channel_not_id=|||||||\"/></outline></body></opml>",
                "",
                null,
                "\uD83D\uDC28\uD83D\uDC28\uD83D\uDC28",
                "gibberish");

        for (String invalidContent : invalidList) {
            try {
                if (invalidContent != null) {
                    byte[] bytes = invalidContent.getBytes("UTF-8");
                    subscriptionTaker.fromInputStream(new ByteArrayInputStream(bytes));
                } else {
                    subscriptionTaker.fromInputStream(null);
                }

                fail("didn't throw exception");
            } catch (Exception e) {
                // System.out.println(" -> " + e);
                boolean isExpectedException = e instanceof SubscriptionTaker.InvalidSourceException;
                assertTrue("\"" + e.getClass().getSimpleName() + "\" is not the expected exception", isExpectedException);
            }
        }
    }
}
