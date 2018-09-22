package com.wintereur.turtletail.taker.services.youtube;

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.ServiceList;
import com.wintereur.turtletail.taker.channel.ChannelTaker;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.services.BaseChannelTakerTest;
import com.wintereur.turtletail.taker.services.youtube.takers.YoutubeChannelTaker;

import static org.junit.Assert.*;
import static com.wintereur.turtletail.taker.TakerAsserts.assertIsSecureUrl;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;
import static com.wintereur.turtletail.taker.services.DefaultTests.*;

/**
 * Test for {@link ChannelTaker}
 */
public class YoutubeChannelTakerTest {
    public static class Gronkh implements BaseChannelTakerTest {
        private static YoutubeChannelTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (YoutubeChannelTaker) YouTube
                    .getChannelTaker("http://www.youtube.com/user/Gronkh");
            taker.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Taker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(YouTube.getServiceId(), taker.getServiceId());
        }

        @Test
        public void testName() throws Exception {
            assertEquals("Gronkh", taker.getName());
        }

        @Test
        public void testId() throws Exception {
            assertEquals("UCYJ61XIK64sp6ZFFS8sctxw", taker.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/channel/UCYJ61XIK64sp6ZFFS8sctxw", taker.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("http://www.youtube.com/user/Gronkh", taker.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            defaultTestRelatedItems(taker, YouTube.getServiceId());
        }

        @Test
        public void testMoreRelatedItems() throws Exception {
            defaultTestMoreItems(taker, ServiceList.YouTube.getServiceId());
        }

         /*//////////////////////////////////////////////////////////////////////////
         // ChannelTaker
         //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testDescription() throws Exception {
            assertTrue(taker.getDescription().contains("Zart im Schmelz und süffig im Abgang. Ungebremster Spieltrieb"));
        }

        @Test
        public void testAvatarUrl() throws Exception {
            String avatarUrl = taker.getAvatarUrl();
            assertIsSecureUrl(avatarUrl);
            assertTrue(avatarUrl, avatarUrl.contains("yt3"));
        }

        @Test
        public void testBannerUrl() throws Exception {
            String bannerUrl = taker.getBannerUrl();
            assertIsSecureUrl(bannerUrl);
            assertTrue(bannerUrl, bannerUrl.contains("yt3"));
        }

        @Test
        public void testFeedUrl() throws Exception {
            assertEquals("https://www.youtube.com/feeds/videos.xml?channel_id=UCYJ61XIK64sp6ZFFS8sctxw", taker.getFeedUrl());
        }

        @Test
        public void testSubscriberCount() throws Exception {
            assertTrue("Wrong subscriber count", taker.getSubscriberCount() >= 0);
        }

        @Test
        public void testChannelDonation() throws Exception {
            // this needs to be ignored since wed have to upgrade channel taker to the new yt interface
            // in order to make this work
            assertTrue(taker.getDonationLinks().length == 0);
        }
    }

    // Youtube RED/Premium ad blocking test
    public static class VSauce implements BaseChannelTakerTest {
        private static YoutubeChannelTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (YoutubeChannelTaker) YouTube
                    .getChannelTaker("https://www.youtube.com/user/Vsauce");
            taker.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Taker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(YouTube.getServiceId(), taker.getServiceId());
        }

        @Test
        public void testName() throws Exception {
            assertEquals("Vsauce", taker.getName());
        }

        @Test
        public void testId() throws Exception {
            assertEquals("UC6nSFpj9HTCZ5t-N3Rm3-HA", taker.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/channel/UC6nSFpj9HTCZ5t-N3Rm3-HA", taker.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/user/Vsauce", taker.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            defaultTestRelatedItems(taker, YouTube.getServiceId());
        }

        @Test
        public void testMoreRelatedItems() throws Exception {
            defaultTestMoreItems(taker, ServiceList.YouTube.getServiceId());
        }

         /*//////////////////////////////////////////////////////////////////////////
         // ChannelTaker
         //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testDescription() throws Exception {
            assertTrue("What it actually was: " + taker.getDescription(),
                    taker.getDescription().contains("Our World is Amazing. Questions? Ideas? Tweet me:"));
        }

        @Test
        public void testAvatarUrl() throws Exception {
            String avatarUrl = taker.getAvatarUrl();
            assertIsSecureUrl(avatarUrl);
            assertTrue(avatarUrl, avatarUrl.contains("yt3"));
        }

        @Test
        public void testBannerUrl() throws Exception {
            String bannerUrl = taker.getBannerUrl();
            assertIsSecureUrl(bannerUrl);
            assertTrue(bannerUrl, bannerUrl.contains("yt3"));
        }

        @Test
        public void testFeedUrl() throws Exception {
            assertEquals("https://www.youtube.com/feeds/videos.xml?channel_id=UC6nSFpj9HTCZ5t-N3Rm3-HA", taker.getFeedUrl());
        }

        @Test
        public void testSubscriberCount() throws Exception {
            assertTrue("Wrong subscriber count", taker.getSubscriberCount() >= 0);
        }

        @Test
        public void testChannelDonation() throws Exception {
            // this needs to be ignored since wed have to upgrade channel taker to the new yt interface
            // in order to make this work
            assertTrue(taker.getDonationLinks().length == 0);
        }
    }

    public static class Kurzgesagt implements BaseChannelTakerTest {
        private static YoutubeChannelTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (YoutubeChannelTaker) YouTube
                    .getChannelTaker("https://www.youtube.com/channel/UCsXVk37bltHxD1rDPwtNM8Q");
            taker.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Additional Testing
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testGetPageInNewTaker() throws Exception {
            final ChannelTaker newTaker = YouTube.getChannelTaker(taker.getUrl());
            defaultTestGetPageInNewTaker(taker, newTaker, YouTube.getServiceId());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Taker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(YouTube.getServiceId(), taker.getServiceId());
        }

        @Test
        public void testName() throws Exception {
            String name = taker.getName();
            assertTrue(name, name.startsWith("Kurzgesagt"));
        }

        @Test
        public void testId() throws Exception {
            assertEquals("UCsXVk37bltHxD1rDPwtNM8Q", taker.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/channel/UCsXVk37bltHxD1rDPwtNM8Q", taker.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/channel/UCsXVk37bltHxD1rDPwtNM8Q", taker.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            defaultTestRelatedItems(taker, YouTube.getServiceId());
        }

        @Test
        public void testMoreRelatedItems() throws Exception {
            defaultTestMoreItems(taker, ServiceList.YouTube.getServiceId());
        }

         /*//////////////////////////////////////////////////////////////////////////
         // ChannelTaker
         //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testDescription() throws Exception {
            final String description = taker.getDescription();
            assertTrue(description, description.contains("small team who want to make science look beautiful"));
            //TODO: Description get cuts out, because the og:description is optimized and don't have all the content
            //assertTrue(description, description.contains("Currently we make one animation video per month"));
        }

        @Test
        public void testAvatarUrl() throws Exception {
            String avatarUrl = taker.getAvatarUrl();
            assertIsSecureUrl(avatarUrl);
            assertTrue(avatarUrl, avatarUrl.contains("yt3"));
        }

        @Test
        public void testBannerUrl() throws Exception {
            String bannerUrl = taker.getBannerUrl();
            assertIsSecureUrl(bannerUrl);
            assertTrue(bannerUrl, bannerUrl.contains("yt3"));
        }

        @Test
        public void testFeedUrl() throws Exception {
            assertEquals("https://www.youtube.com/feeds/videos.xml?channel_id=UCsXVk37bltHxD1rDPwtNM8Q", taker.getFeedUrl());
        }

        @Test
        public void testSubscriberCount() throws Exception {
            assertTrue("Wrong subscriber count", taker.getSubscriberCount() >= 5e6);
        }

        @Test
        public void testChannelDonation() throws Exception {
            assertTrue(taker.getDonationLinks().length == 1);
        }
    }

    public static class CaptainDisillusion implements BaseChannelTakerTest {
        private static YoutubeChannelTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (YoutubeChannelTaker) YouTube
                    .getChannelTaker("https://www.youtube.com/user/CaptainDisillusion/videos");
            taker.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Taker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(YouTube.getServiceId(), taker.getServiceId());
        }

        @Test
        public void testName() throws Exception {
            assertEquals("CaptainDisillusion", taker.getName());
        }

        @Test
        public void testId() throws Exception {
            assertEquals("UCEOXxzW2vU0P-0THehuIIeg", taker.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/channel/UCEOXxzW2vU0P-0THehuIIeg", taker.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/user/CaptainDisillusion/videos", taker.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            defaultTestRelatedItems(taker, YouTube.getServiceId());
        }

        @Test
        public void testMoreRelatedItems() throws Exception {
            defaultTestMoreItems(taker, ServiceList.YouTube.getServiceId());
        }

         /*//////////////////////////////////////////////////////////////////////////
         // ChannelTaker
         //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testDescription() throws Exception {
            final String description = taker.getDescription();
            assertTrue(description, description.contains("In a world where"));
        }

        @Test
        public void testAvatarUrl() throws Exception {
            String avatarUrl = taker.getAvatarUrl();
            assertIsSecureUrl(avatarUrl);
            assertTrue(avatarUrl, avatarUrl.contains("yt3"));
        }

        @Test
        public void testBannerUrl() throws Exception {
            String bannerUrl = taker.getBannerUrl();
            assertIsSecureUrl(bannerUrl);
            assertTrue(bannerUrl, bannerUrl.contains("yt3"));
        }

        @Test
        public void testFeedUrl() throws Exception {
            assertEquals("https://www.youtube.com/feeds/videos.xml?channel_id=UCEOXxzW2vU0P-0THehuIIeg", taker.getFeedUrl());
        }

        @Test
        public void testSubscriberCount() throws Exception {
            assertTrue("Wrong subscriber count", taker.getSubscriberCount() >= 5e5);
        }
    }

    public static class RandomChannel implements BaseChannelTakerTest {
        private static YoutubeChannelTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (YoutubeChannelTaker) YouTube
                    .getChannelTaker("https://www.youtube.com/channel/UCUaQMQS9lY5lit3vurpXQ6w");
            taker.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Taker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(YouTube.getServiceId(), taker.getServiceId());
        }

        @Test
        public void testName() throws Exception {
            assertEquals("random channel", taker.getName());
        }

        @Test
        public void testId() throws Exception {
            assertEquals("UCUaQMQS9lY5lit3vurpXQ6w", taker.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/channel/UCUaQMQS9lY5lit3vurpXQ6w", taker.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/channel/UCUaQMQS9lY5lit3vurpXQ6w", taker.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            defaultTestRelatedItems(taker, YouTube.getServiceId());
        }

        @Test
        public void testMoreRelatedItems() {
            try {
                defaultTestMoreItems(taker, YouTube.getServiceId());
            } catch (Throwable ignored) {
                return;
            }

            fail("This channel doesn't have more items, it should throw an error");
        }

         /*//////////////////////////////////////////////////////////////////////////
         // ChannelTaker
         //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testDescription() throws Exception {
            final String description = taker.getDescription();
            assertTrue(description, description.contains("Hey there iu will upoload a load of pranks onto this channel"));
        }

        @Test
        public void testAvatarUrl() throws Exception {
            String avatarUrl = taker.getAvatarUrl();
            assertIsSecureUrl(avatarUrl);
            assertTrue(avatarUrl, avatarUrl.contains("yt3"));
        }

        @Test
        public void testBannerUrl() throws Exception {
            String bannerUrl = taker.getBannerUrl();
            assertIsSecureUrl(bannerUrl);
            assertTrue(bannerUrl, bannerUrl.contains("yt3"));
        }

        @Test
        public void testFeedUrl() throws Exception {
            assertEquals("https://www.youtube.com/feeds/videos.xml?channel_id=UCUaQMQS9lY5lit3vurpXQ6w", taker.getFeedUrl());
        }

        @Test
        public void testSubscriberCount() throws Exception {
            assertTrue("Wrong subscriber count", taker.getSubscriberCount() >= 50);
        }

        @Test
        public void testChannelDonation() throws Exception {
            assertTrue(taker.getDonationLinks().length == 0);
        }
    }
};

