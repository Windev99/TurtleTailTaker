package com.wintereur.turtletail.taker.services.youtube;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.services.youtube.takers.YoutubeStreamTaker;
import com.wintereur.turtletail.taker.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory;
import com.wintereur.turtletail.taker.stream.StreamTaker;
import com.wintereur.turtletail.taker.stream.SubtitlesFormat;
import com.wintereur.turtletail.taker.stream.VideoStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static com.wintereur.turtletail.taker.TakerAsserts.assertIsSecureUrl;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;

/**
 * Test for {@link YoutubeStreamLinkHandlerFactory}
 */
public class YoutubeStreamTakerControversialTest {
    private static YoutubeStreamTaker taker;

    @BeforeClass
    public static void setUp() throws Exception {
        TurtleTail.init(Downloader.getInstance());
        taker = (YoutubeStreamTaker) YouTube
                .getStreamTaker("https://www.youtube.com/watch?v=T4XJQO3qol8");
        taker.fetchPage();
    }

    @Test
    public void testGetInvalidTimeStamp() throws ParsingException {
        assertTrue(taker.getTimeStamp() + "", taker.getTimeStamp() <= 0);
    }

    @Test
    public void testGetValidTimeStamp() throws IOException, ExtractionException {
        StreamTaker taker = YouTube.getStreamTaker("https://youtu.be/FmG385_uUys?t=174");
        assertEquals(taker.getTimeStamp() + "", "174");
    }

    @Test
    @Ignore
    public void testGetAgeLimit() throws ParsingException {
        assertEquals(18, taker.getAgeLimit());
    }

    @Test
    public void testGetName() throws ParsingException {
        assertNotNull("name is null", taker.getName());
        assertFalse("name is empty", taker.getName().isEmpty());
    }

    @Test
    public void testGetDescription() throws ParsingException {
        assertNotNull(taker.getDescription());
//        assertFalse(taker.getDescription().isEmpty());
    }

    @Test
    public void testGetUploaderName() throws ParsingException {
        assertNotNull(taker.getUploaderName());
        assertFalse(taker.getUploaderName().isEmpty());
    }

    @Ignore // Currently there is no way get the length from restricted videos
    @Test
    public void testGetLength() throws ParsingException {
        assertTrue(taker.getLength() > 0);
    }

    @Test
    public void testGetViews() throws ParsingException {
        assertTrue(taker.getViewCount() > 0);
    }

    @Test
    public void testGetUploadDate() throws ParsingException {
        assertTrue(taker.getUploadDate().length() > 0);
    }

    @Test
    public void testGetThumbnailUrl() throws ParsingException {
        assertIsSecureUrl(taker.getThumbnailUrl());
    }

    @Test
    public void testGetUploaderAvatarUrl() throws ParsingException {
        assertIsSecureUrl(taker.getUploaderAvatarUrl());
    }

    // FIXME: 25.11.17 Are there no streams or are they not listed?
    @Ignore
    @Test
    public void testGetAudioStreams() throws IOException, ExtractionException {
        // audio streams are not always necessary
        assertFalse(taker.getAudioStreams().isEmpty());
    }

    @Test
    public void testGetVideoStreams() throws IOException, ExtractionException {
        List<VideoStream> streams = new ArrayList<>();
        streams.addAll(taker.getVideoStreams());
        streams.addAll(taker.getVideoOnlyStreams());
        assertTrue(streams.size() > 0);
    }

    @Ignore
    @Test
    public void testGetSubtitlesListDefault() throws IOException, ExtractionException {
        // Video (/view?v=YQHsXMglC9A) set in the setUp() method has no captions => null
        assertTrue(!taker.getSubtitlesDefault().isEmpty());
    }

    @Ignore
    @Test
    public void testGetSubtitlesList() throws IOException, ExtractionException {
        // Video (/view?v=YQHsXMglC9A) set in the setUp() method has no captions => null
        assertTrue(!taker.getSubtitles(SubtitlesFormat.TTML).isEmpty());
    }
}
