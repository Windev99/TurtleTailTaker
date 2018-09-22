package com.wintereur.turtletail.taker.services.soundcloud;

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.channel.ChannelTaker;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.services.BaseChannelTakerTest;

import static org.junit.Assert.*;
import static com.wintereur.turtletail.taker.TakerAsserts.assertEmpty;
import static com.wintereur.turtletail.taker.TakerAsserts.assertIsSecureUrl;
import static com.wintereur.turtletail.taker.ServiceList.SoundCloud;
import static com.wintereur.turtletail.taker.services.DefaultTests.*;

/**
 * Test for {@link SoundcloudChannelTaker}
 */
public class SoundcloudChannelTakerTest {
    public static class LilUzi implements BaseChannelTakerTest {
        private static SoundcloudChannelTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (SoundcloudChannelTaker) SoundCloud
                    .getChannelTaker("http://soundcloud.com/liluzivert/sets");
            taker.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Taker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(SoundCloud.getServiceId(), taker.getServiceId());
        }

        @Test
        public void testName() {
            assertEquals("LIL UZI VERT", taker.getName());
        }

        @Test
        public void testId() {
            assertEquals("10494998", taker.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://soundcloud.com/liluzivert", taker.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("http://soundcloud.com/liluzivert/sets", taker.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            defaultTestRelatedItems(taker, SoundCloud.getServiceId());
        }

        @Test
        public void testMoreRelatedItems() throws Exception {
            defaultTestMoreItems(taker, SoundCloud.getServiceId());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ChannelTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testDescription() {
            assertNotNull(taker.getDescription());
        }

        @Test
        public void testAvatarUrl() {
            assertIsSecureUrl(taker.getAvatarUrl());
        }

        @Test
        public void testBannerUrl() {
            assertIsSecureUrl(taker.getBannerUrl());
        }

        @Test
        public void testFeedUrl() {
            assertEmpty(taker.getFeedUrl());
        }

        @Test
        public void testSubscriberCount() {
            assertTrue("Wrong subscriber count", taker.getSubscriberCount() >= 1e6);
        }
    }

    public static class DubMatix implements BaseChannelTakerTest {
        private static SoundcloudChannelTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (SoundcloudChannelTaker) SoundCloud
                    .getChannelTaker("https://soundcloud.com/dubmatix");
            taker.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Additional Testing
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testGetPageInNewTaker() throws Exception {
            final ChannelTaker newTaker = SoundCloud.getChannelTaker(taker.getUrl());
            defaultTestGetPageInNewTaker(taker, newTaker, SoundCloud.getServiceId());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Taker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(SoundCloud.getServiceId(), taker.getServiceId());
        }

        @Test
        public void testName() {
            assertEquals("dubmatix", taker.getName());
        }

        @Test
        public void testId() {
            assertEquals("542134", taker.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://soundcloud.com/dubmatix", taker.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("https://soundcloud.com/dubmatix", taker.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            defaultTestRelatedItems(taker, SoundCloud.getServiceId());
        }

        @Test
        public void testMoreRelatedItems() throws Exception {
            defaultTestMoreItems(taker, SoundCloud.getServiceId());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ChannelTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testDescription() {
            assertNotNull(taker.getDescription());
        }

        @Test
        public void testAvatarUrl() {
            assertIsSecureUrl(taker.getAvatarUrl());
        }

        @Test
        public void testBannerUrl() {
            assertIsSecureUrl(taker.getBannerUrl());
        }

        @Test
        public void testFeedUrl() {
            assertEmpty(taker.getFeedUrl());
        }

        @Test
        public void testSubscriberCount() {
            assertTrue("Wrong subscriber count", taker.getSubscriberCount() >= 2e6);
        }
    }
}
