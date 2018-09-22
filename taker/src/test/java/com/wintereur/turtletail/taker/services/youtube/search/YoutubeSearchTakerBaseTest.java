package com.wintereur.turtletail.taker.services.youtube.search;

import org.junit.Test;
import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.services.BaseListTakerTest;
import com.wintereur.turtletail.taker.services.youtube.takers.YoutubeSearchTaker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/*
 * Created by Christian Schabesberger on 27.05.18
 *
 * Copyright (C) Christian Schabesberger 2018 <chris.schabesberger@mailbox.com>
 * YoutubeSearchTakerBaseTest.java is part of TurtleTail.
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
 * Test for {@link YoutubeSearchTaker}
 */
public abstract class YoutubeSearchTakerBaseTest {

    protected static YoutubeSearchTaker taker;
    protected static ListTaker.InfoItemsPage<InfoItem> itemsPage;


    @Test
    public void testResultListElementsLength() {
        assertTrue(Integer.toString(itemsPage.getItems().size()),
                itemsPage.getItems().size() > 10);
    }

    @Test
    public void testUrl() throws Exception {
        assertTrue(taker.getUrl(), taker.getUrl().startsWith("https://www.youtube.com"));
    }
}
