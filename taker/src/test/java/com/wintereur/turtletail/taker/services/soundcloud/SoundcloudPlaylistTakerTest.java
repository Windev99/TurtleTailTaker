package com.wintereur.turtletail.taker.services.soundcloud;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.ServiceList;
import com.wintereur.turtletail.taker.playlist.PlaylistTaker;
import com.wintereur.turtletail.taker.services.BasePlaylistTakerTest;
import com.wintereur.turtletail.taker.stream.StreamInfoItem;

import static org.junit.Assert.*;
import static com.wintereur.turtletail.taker.TakerAsserts.assertIsSecureUrl;
import static com.wintereur.turtletail.taker.ServiceList.SoundCloud;
import static com.wintereur.turtletail.taker.services.DefaultTests.*;

/**
 * Test for {@link PlaylistTaker}
 */
public class SoundcloudPlaylistTakerTest {
    public static class LuvTape implements BasePlaylistTakerTest {
        private static SoundcloudPlaylistTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (SoundcloudPlaylistTaker) SoundCloud
                    .getPlaylistTaker("https://soundcloud.com/liluzivert/sets/the-perfect-luv-tape-r?test=123");
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
            assertEquals("THE PERFECT LUV TAPE®️", taker.getName());
        }

        @Test
        public void testId() {
            assertEquals("246349810", taker.getId());
        }

        @Test
        public void testUrl() throws Exception {
            assertEquals("https://soundcloud.com/liluzivert/sets/the-perfect-luv-tape-r", taker.getUrl());
        }

        @Test
        public void testOriginalUrl() throws Exception {
            assertEquals("https://soundcloud.com/liluzivert/sets/the-perfect-luv-tape-r?test=123", taker.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            defaultTestRelatedItems(taker, SoundCloud.getServiceId());
        }

        @Test
        public void testMoreRelatedItems() {
            try {
                defaultTestMoreItems(taker, SoundCloud.getServiceId());
            } catch (Throwable ignored) {
                return;
            }

            fail("This playlist doesn't have more items, it should throw an error");
        }

        /*//////////////////////////////////////////////////////////////////////////
        // PlaylistTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testThumbnailUrl() {
            assertIsSecureUrl(taker.getThumbnailUrl());
        }

        @Ignore
        @Test
        public void testBannerUrl() {
            assertIsSecureUrl(taker.getBannerUrl());
        }

        @Test
        public void testUploaderUrl() {
            final String uploaderUrl = taker.getUploaderUrl();
            assertIsSecureUrl(uploaderUrl);
            assertTrue(uploaderUrl, uploaderUrl.contains("liluzivert"));
        }

        @Test
        public void testUploaderName() {
            assertTrue(taker.getUploaderName().contains("LIL UZI VERT"));
        }

        @Test
        public void testUploaderAvatarUrl() {
            assertIsSecureUrl(taker.getUploaderAvatarUrl());
        }

        @Test
        public void testStreamCount() {
            assertTrue("Error in the streams count", taker.getStreamCount() >= 10);
        }
    }

    public static class RandomHouseDanceMusic implements BasePlaylistTakerTest {
        private static SoundcloudPlaylistTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (SoundcloudPlaylistTaker) SoundCloud
                    .getPlaylistTaker("https://soundcloud.com/hunter-leader/sets/house-electro-dance-music-2");
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
            assertEquals("House, Electro , Dance Music 2", taker.getName());
        }

        @Test
        public void testId() {
            assertEquals("310980722", taker.getId());
        }

        @Test
        public void testUrl() throws Exception {
            assertEquals("https://soundcloud.com/hunter-leader/sets/house-electro-dance-music-2", taker.getUrl());
        }

        @Test
        public void testOriginalUrl() throws Exception {
            assertEquals("https://soundcloud.com/hunter-leader/sets/house-electro-dance-music-2", taker.getOriginalUrl());
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
        // PlaylistTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testThumbnailUrl() {
            assertIsSecureUrl(taker.getThumbnailUrl());
        }

        @Ignore
        @Test
        public void testBannerUrl() {
            assertIsSecureUrl(taker.getBannerUrl());
        }

        @Test
        public void testUploaderUrl() {
            final String uploaderUrl = taker.getUploaderUrl();
            assertIsSecureUrl(uploaderUrl);
            assertTrue(uploaderUrl, uploaderUrl.contains("hunter-leader"));
        }

        @Test
        public void testUploaderName() {
            assertEquals("Gosu", taker.getUploaderName());
        }

        @Test
        public void testUploaderAvatarUrl() {
            assertIsSecureUrl(taker.getUploaderAvatarUrl());
        }

        @Test
        public void testStreamCount() {
            assertTrue("Error in the streams count", taker.getStreamCount() >= 10);
        }
    }

    public static class EDMxxx implements BasePlaylistTakerTest {
        private static SoundcloudPlaylistTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (SoundcloudPlaylistTaker) SoundCloud
                    .getPlaylistTaker("https://soundcloud.com/user350509423/sets/edm-xxx");
            taker.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Additional Testing
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testGetPageInNewTaker() throws Exception {
            final PlaylistTaker newTaker = SoundCloud.getPlaylistTaker(taker.getUrl());
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
            assertEquals("EDM xXx", taker.getName());
        }

        @Test
        public void testId() {
            assertEquals("136000376", taker.getId());
        }

        @Test
        public void testUrl() throws Exception {
            assertEquals("https://soundcloud.com/user350509423/sets/edm-xxx", taker.getUrl());
        }

        @Test
        public void testOriginalUrl() throws Exception {
            assertEquals("https://soundcloud.com/user350509423/sets/edm-xxx", taker.getOriginalUrl());
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
            ListTaker.InfoItemsPage<StreamInfoItem> currentPage = defaultTestMoreItems(taker, ServiceList.SoundCloud.getServiceId());
            // Test for 2 more levels
            for (int i = 0; i < 2; i++) {
                currentPage = taker.getPage(currentPage.getNextPageUrl());
                defaultTestListOfItems(SoundCloud.getServiceId(), currentPage.getItems(), currentPage.getErrors());
            }
        }

        /*//////////////////////////////////////////////////////////////////////////
        // PlaylistTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testThumbnailUrl() {
            assertIsSecureUrl(taker.getThumbnailUrl());
        }

        @Ignore
        @Test
        public void testBannerUrl() {
            assertIsSecureUrl(taker.getBannerUrl());
        }

        @Test
        public void testUploaderUrl() {
            final String uploaderUrl = taker.getUploaderUrl();
            assertIsSecureUrl(uploaderUrl);
            assertTrue(uploaderUrl, uploaderUrl.contains("user350509423"));
        }

        @Test
        public void testUploaderName() {
            assertEquals("user350509423", taker.getUploaderName());
        }

        @Test
        public void testUploaderAvatarUrl() {
            assertIsSecureUrl(taker.getUploaderAvatarUrl());
        }

        @Test
        public void testStreamCount() {
            assertTrue("Error in the streams count", taker.getStreamCount() >= 3900);
        }
    }
}
