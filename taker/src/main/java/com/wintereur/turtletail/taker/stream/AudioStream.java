package com.wintereur.turtletail.taker.stream;

/*
 * Created by Christian Schabesberger on 04.03.16.
 *
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.com>
 * AudioStream.java is part of TurtleTail.
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

import com.wintereur.turtletail.taker.MediaFormat;

public class AudioStream extends Stream {
    public int average_bitrate = -1;

    /**
     * Create a new audio stream
     * @param url the url
     * @param format the format
     * @param averageBitrate the average bitrate
     */
    public AudioStream(String url, MediaFormat format, int averageBitrate) {
        super(url, format);
        this.average_bitrate = averageBitrate;
    }

    @Override
    public boolean equalStats(Stream cmp) {
        return super.equalStats(cmp) && cmp instanceof AudioStream &&
                average_bitrate == ((AudioStream) cmp).average_bitrate;
    }

    /**
     * Get the average bitrate
     * @return the average bitrate or -1
     */
    public int getAverageBitrate() {
        return average_bitrate;
    }
}
