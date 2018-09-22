package com.wintereur.turtletail.taker.services.soundcloud;

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.SuggestionTaker;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static com.wintereur.turtletail.taker.ServiceList.SoundCloud;

/**
 * Test for {@link SuggestionTaker}
 */
public class SoundcloudSuggestionTakerTest {
    private static SuggestionTaker suggestionTaker;

    @BeforeClass
    public static void setUp() throws Exception {
        TurtleTail.init(Downloader.getInstance());
        suggestionTaker = SoundCloud.getSuggestionTaker();
    }

    @Test
    public void testIfSuggestions() throws IOException, ExtractionException {
        assertFalse(suggestionTaker.suggestionList("lil uzi vert", "de").isEmpty());
    }
}
