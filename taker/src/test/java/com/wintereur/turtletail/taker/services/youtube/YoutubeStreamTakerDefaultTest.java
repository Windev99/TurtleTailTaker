package com.wintereur.turtletail.taker.services.youtube;

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.services.youtube.takers.YoutubeStreamTaker;
import com.wintereur.turtletail.taker.stream.*;
import com.wintereur.turtletail.taker.utils.DashMpdParser;
import com.wintereur.turtletail.taker.utils.Utils;

import java.io.IOException;

import static org.junit.Assert.*;
import static com.wintereur.turtletail.taker.TakerAsserts.assertIsSecureUrl;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;
import static com.wintereur.turtletail.taker.services.youtube.YoutubeTrendingTakerTest.taker;

/*
 * Created by Christian Schabesberger on 30.12.15.
 *
 * Copyright (C) Christian Schabesberger 2015 <chris.schabesberger@mailbox.com>
 * YoutubeVideoTakerDefault.java is part of TurtleTail.
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
 * Test for {@link StreamTaker}
 */
public class YoutubeStreamTakerDefaultTest {

    public static class AdeleHello {
        private static YoutubeStreamTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (YoutubeStreamTaker) YouTube
                    .getStreamTaker("https://www.youtube.com/watch?v=rYEDA3JcQqw");
            taker.fetchPage();
        }

        @Test
        public void testGetInvalidTimeStamp() throws ParsingException {
            assertTrue(taker.getTimeStamp() + "",
                    taker.getTimeStamp() <= 0);
        }

        @Test
        public void testGetValidTimeStamp() throws ExtractionException {
            StreamTaker taker = YouTube.getStreamTaker("https://youtu.be/FmG385_uUys?t=174");
            assertEquals(taker.getTimeStamp() + "", "174");
        }

        @Test
        public void testGetTitle() throws ParsingException {
            assertFalse(taker.getName().isEmpty());
        }

        @Test
        public void testGetDescription() throws ParsingException {
            assertNotNull(taker.getDescription());
            assertFalse(taker.getDescription().isEmpty());
        }

        @Test
        public void testGetFullLinksInDescriptlion() throws ParsingException {
            assertTrue(taker.getDescription().contains("http://smarturl.it/SubscribeAdele?IQid=yt"));
            assertFalse(taker.getDescription().contains("http://smarturl.it/SubscribeAdele?IQi..."));
        }

        @Test
        public void testGetUploaderName() throws ParsingException {
            assertNotNull(taker.getUploaderName());
            assertFalse(taker.getUploaderName().isEmpty());
        }


        @Test
        public void testGetLength() throws ParsingException {
            assertTrue(taker.getLength() > 0);
        }

        @Test
        public void testGetViewCount() throws ParsingException {
            Long count = taker.getViewCount();
            assertTrue(Long.toString(count), count >= /* specific to that video */ 1220025784);
        }

        @Test
        public void testGetUploadDate() throws ParsingException {
            assertTrue(taker.getUploadDate().length() > 0);
        }

        @Test
        public void testGetUploaderUrl() throws ParsingException {
            assertTrue(taker.getUploaderUrl().length() > 0);
        }

        @Test
        public void testGetThumbnailUrl() throws ParsingException {
            assertIsSecureUrl(taker.getThumbnailUrl());
        }

        @Test
        public void testGetUploaderAvatarUrl() throws ParsingException {
            assertIsSecureUrl(taker.getUploaderAvatarUrl());
        }

        @Test
        public void testGetAudioStreams() throws IOException, ExtractionException {
            assertFalse(taker.getAudioStreams().isEmpty());
        }

        @Test
        public void testGetVideoStreams() throws IOException, ExtractionException {
            for (VideoStream s : taker.getVideoStreams()) {
                assertIsSecureUrl(s.url);
                assertTrue(s.resolution.length() > 0);
                assertTrue(Integer.toString(s.getFormatId()),
                        0 <= s.getFormatId() && s.getFormatId() <= 4);
            }
        }

        @Test
        public void testStreamType() throws ParsingException {
            assertTrue(taker.getStreamType() == StreamType.VIDEO_STREAM);
        }

        @Test
        public void testGetDashMpd() throws ParsingException {
            // we dont expect this particular video to have a DASH file. For this purpouse we use a different test class.
            assertTrue(taker.getDashMpdUrl(),
                    taker.getDashMpdUrl() != null && taker.getDashMpdUrl().isEmpty());
        }

        @Test
        public void testGetRelatedVideos() throws ExtractionException, IOException {
            StreamInfoItemsCollector relatedVideos = taker.getRelatedVideos();
            Utils.printErrors(relatedVideos.getErrors());
            assertFalse(relatedVideos.getItems().isEmpty());
            assertTrue(relatedVideos.getErrors().isEmpty());
        }

        @Test
        public void testGetSubtitlesListDefault() throws IOException, ExtractionException {
            // Video (/view?v=YQHsXMglC9A) set in the setUp() method has no captions => null
            assertTrue(taker.getSubtitlesDefault().isEmpty());
        }

        @Test
        public void testGetSubtitlesList() throws IOException, ExtractionException {
            // Video (/view?v=YQHsXMglC9A) set in the setUp() method has no captions => null
            assertTrue(taker.getSubtitles(SubtitlesFormat.TTML).isEmpty());
        }
    }

    public static class DescriptionTestPewdiepie {
        private static YoutubeStreamTaker taker;

        @BeforeClass
        public static void setUp() throws Exception {
            TurtleTail.init(Downloader.getInstance());
            taker = (YoutubeStreamTaker) YouTube
                    .getStreamTaker("https://www.youtube.com/watch?v=dJY8iT341F4");
            taker.fetchPage();
        }

        @Test
        public void testGetDescription() throws ParsingException {
            assertNotNull(taker.getDescription());
            assertFalse(taker.getDescription().isEmpty());
        }

        @Test
        public void testGetFullLinksInDescriptlion() throws ParsingException {
            assertTrue(taker.getDescription().contains("https://www.reddit.com/r/PewdiepieSubmissions/"));
            assertTrue(taker.getDescription().contains("https://www.youtube.com/channel/UC3e8EMTOn4g6ZSKggHTnNng"));

            assertFalse(taker.getDescription().contains("https://www.reddit.com/r/PewdiepieSub..."));
            assertFalse(taker.getDescription().contains("https://usa.clutchchairz.com/product/..."));
            assertFalse(taker.getDescription().contains("https://europe.clutchchairz.com/en/pr..."));
            assertFalse(taker.getDescription().contains("https://canada.clutchchairz.com/produ..."));
            assertFalse(taker.getDescription().contains("http://store.steampowered.com/app/703..."));
            assertFalse(taker.getDescription().contains("https://www.youtube.com/channel/UC3e8..."));
        }
    }
}
