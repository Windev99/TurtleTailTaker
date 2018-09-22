package com.wintereur.turtletail.taker.services.youtube;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.ServiceList;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.playlist.PlaylistTaker;
import com.wintereur.turtletail.taker.services.BasePlaylistTakerTest;
import com.wintereur.turtletail.taker.services.youtube.takers.YoutubePlaylistTaker;
import com.wintereur.turtletail.taker.stream.StreamInfoItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static com.wintereur.turtletail.taker.TakerAsserts.assertIsSecureUrl;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;
import static com.wintereur.turtletail.taker.services.DefaultTests.*;

/**
 * Test for {@link YoutubePlaylistTaker}
 */
public class YoutubePlaylistTakerTest {
    public static class TimelessPopHits implements BasePlaylistTakerTest {
        private static YoutubePlaylistTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (YoutubePlaylistTaker) YouTube
                    .getPlaylistTaker("http://www.youtube.com/watch?v=lp-EO5I60KA&list=PLMC9KNkIncKtPzgY-5rmhvj7fax8fdxoj");
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
            String name = taker.getName();
            assertTrue(name, name.startsWith("Pop Music Playlist"));
        }

        @Test
        public void testId() throws Exception {
            assertEquals("PLMC9KNkIncKtPzgY-5rmhvj7fax8fdxoj", taker.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/playlist?list=PLMC9KNkIncKtPzgY-5rmhvj7fax8fdxoj", taker.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("http://www.youtube.com/watch?v=lp-EO5I60KA&list=PLMC9KNkIncKtPzgY-5rmhvj7fax8fdxoj", taker.getOriginalUrl());
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
        // PlaylistTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testThumbnailUrl() throws Exception {
            final String thumbnailUrl = taker.getThumbnailUrl();
            assertIsSecureUrl(thumbnailUrl);
            assertTrue(thumbnailUrl, thumbnailUrl.contains("yt"));
        }

        @Ignore
        @Test
        public void testBannerUrl() throws Exception {
            final String bannerUrl = taker.getBannerUrl();
            assertIsSecureUrl(bannerUrl);
            assertTrue(bannerUrl, bannerUrl.contains("yt"));
        }

        @Test
        public void testUploaderUrl() throws Exception {
            assertTrue(taker.getUploaderUrl().contains("youtube.com"));
        }

        @Test
        public void testUploaderName() throws Exception {
            final String uploaderName = taker.getUploaderName();
            assertTrue(uploaderName, uploaderName.contains("Just Hits"));
        }

        @Test
        public void testUploaderAvatarUrl() throws Exception {
            final String uploaderAvatarUrl = taker.getUploaderAvatarUrl();
            assertTrue(uploaderAvatarUrl, uploaderAvatarUrl.contains("yt"));
        }

        @Test
        public void testStreamCount() throws Exception {
            assertTrue("Error in the streams count", taker.getStreamCount() > 100);
        }
    }

    public static class HugePlaylist implements BasePlaylistTakerTest {
        private static YoutubePlaylistTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (YoutubePlaylistTaker) YouTube
                    .getPlaylistTaker("https://www.youtube.com/watch?v=8SbUC-UaAxE&list=PLWwAypAcFRgKAIIFqBr9oy-ZYZnixa_Fj");
            taker.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Additional Testing
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testGetPageInNewTaker() throws Exception {
            final PlaylistTaker newTaker = YouTube.getPlaylistTaker(taker.getUrl());
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
            final String name = taker.getName();
            assertEquals("I Wanna Rock Super Gigantic Playlist 1: Hardrock, AOR, Metal and more !!! 5000 music videos !!!", name);
        }

        @Test
        public void testId() throws Exception {
            assertEquals("PLWwAypAcFRgKAIIFqBr9oy-ZYZnixa_Fj", taker.getId());
        }

        @Test
        public void testUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/playlist?list=PLWwAypAcFRgKAIIFqBr9oy-ZYZnixa_Fj", taker.getUrl());
        }

        @Test
        public void testOriginalUrl() throws ParsingException {
            assertEquals("https://www.youtube.com/watch?v=8SbUC-UaAxE&list=PLWwAypAcFRgKAIIFqBr9oy-ZYZnixa_Fj", taker.getOriginalUrl());
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
            ListTaker.InfoItemsPage<StreamInfoItem> currentPage
                    = defaultTestMoreItems(taker, ServiceList.YouTube.getServiceId());
            // Test for 2 more levels

            for (int i = 0; i < 2; i++) {
                currentPage = taker.getPage(currentPage.getNextPageUrl());
                defaultTestListOfItems(YouTube.getServiceId(), currentPage.getItems(), currentPage.getErrors());
            }
        }

        /*//////////////////////////////////////////////////////////////////////////
        // PlaylistTaker
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testThumbnailUrl() throws Exception {
            final String thumbnailUrl = taker.getThumbnailUrl();
            assertIsSecureUrl(thumbnailUrl);
            assertTrue(thumbnailUrl, thumbnailUrl.contains("yt"));
        }

        @Ignore
        @Test
        public void testBannerUrl() throws Exception {
            final String bannerUrl = taker.getBannerUrl();
            assertIsSecureUrl(bannerUrl);
            assertTrue(bannerUrl, bannerUrl.contains("yt"));
        }

        @Test
        public void testUploaderUrl() throws Exception {
            assertTrue(taker.getUploaderUrl().contains("youtube.com"));
        }

        @Test
        public void testUploaderName() throws Exception {
            assertEquals("Tomas Nilsson", taker.getUploaderName());
        }

        @Test
        public void testUploaderAvatarUrl() throws Exception {
            final String uploaderAvatarUrl = taker.getUploaderAvatarUrl();
            assertTrue(uploaderAvatarUrl, uploaderAvatarUrl.contains("yt"));
        }

        @Test
        public void testStreamCount() throws Exception {
            assertTrue("Error in the streams count", taker.getStreamCount() > 100);
        }
    }
}
