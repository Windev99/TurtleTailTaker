package com.wintereur.turtletail.taker.services.youtube;


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

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.stream.StreamTaker;
import com.wintereur.turtletail.taker.stream.StreamInfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;

/**
 * Test for {@link StreamTaker}
 */
public class YoutubeStreamTakerDASHTest {
    private static StreamInfo info;

    @BeforeClass
    public static void setUp() throws Exception {
        TurtleTail.init(Downloader.getInstance());
        info = StreamInfo.getInfo(YouTube, "https://www.youtube.com/watch?v=00Q4SUnVQK4");
    }

    @Test
    public void testGetDashMpd() {
        System.out.println(info.getDashMpdUrl());
        assertTrue(info.getDashMpdUrl(),
                info.getDashMpdUrl() != null && !info.getDashMpdUrl().isEmpty());
    }

    @Test
    public void testDashMpdParser() {
        assertEquals(0, info.getAudioStreams().size());
        assertEquals(0, info.getVideoOnlyStreams().size());
        assertEquals(4, info.getVideoStreams().size());
    }
}
