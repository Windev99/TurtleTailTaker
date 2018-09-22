package com.wintereur.turtletail.taker.services.youtube;

/*
 * Created by Christian Schabesberger on 29.12.15.
 *
 * Copyright (C) Christian Schabesberger 2015 <chris.schabesberger@mailbox.com>
 * YoutubeSearchTakerStreamTest.java is part of TurtleTail.
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
import com.wintereur.turtletail.taker.StreamingService;
import com.wintereur.turtletail.taker.kiosk.KioskList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;

/**
 * Test for {@link YoutubeService}
 */
public class YoutubeServiceTest {
    static StreamingService service;
    static KioskList kioskList;

    @BeforeClass
    public static void setUp() throws Exception {
        TurtleTail.init(Downloader.getInstance());
        service = YouTube;
        kioskList = service.getKioskList();
    }

    @Test
    public void testGetKioskAvailableKiosks() throws Exception {
        assertFalse("No kiosk got returned", kioskList.getAvailableKiosks().isEmpty());
    }

    @Test
    public void testGetDefaultKiosk() throws Exception {
        assertEquals(kioskList.getDefaultKioskTaker(null).getId(), "Trending");
    }
}
