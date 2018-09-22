package com.wintereur.turtletail.taker.services.youtube;

/*
 * Created by Christian Schabesberger on 18.11.16.
 *
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.com>
 * YoutubeSuggestionTakerTest.java is part of TurtleTail.
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

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.SuggestionTaker;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;

/**
 * Test for {@link SuggestionTaker}
 */
public class YoutubeSuggestionTakerTest {
    private static SuggestionTaker suggestionTaker;

    @BeforeClass
    public static void setUp() throws Exception {
        TurtleTail.init(Downloader.getInstance());
        suggestionTaker = YouTube.getSuggestionTaker();
    }

    @Test
    public void testIfSuggestions() throws IOException, ExtractionException {
        assertFalse(suggestionTaker.suggestionList("hello", "de").isEmpty());
    }
}
