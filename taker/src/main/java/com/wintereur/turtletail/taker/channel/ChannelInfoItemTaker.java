package com.wintereur.turtletail.taker.channel;

import com.wintereur.turtletail.taker.InfoItemTaker;
import com.wintereur.turtletail.taker.exceptions.ParsingException;

/*
 * Created by Christian Schabesberger on 12.02.17.
 *
 * Copyright (C) Christian Schabesberger 2017 <chris.schabesberger@mailbox.com>
 * ChannelInfoItemTaker.java is part of TurtleTail.
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

public interface ChannelInfoItemTaker extends InfoItemTaker {
    String getDescription() throws ParsingException;

    long getSubscriberCount() throws ParsingException;
    long getStreamCount() throws ParsingException;
}
