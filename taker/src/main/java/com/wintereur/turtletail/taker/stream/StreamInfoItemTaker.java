package com.wintereur.turtletail.taker.stream;

import com.wintereur.turtletail.taker.InfoItemTaker;
import com.wintereur.turtletail.taker.exceptions.ParsingException;

/*
 * Created by Christian Schabesberger on 28.02.16.
 *
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.com>
 * StreamInfoItemTaker.java is part of TurtleTail.
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

public interface StreamInfoItemTaker extends InfoItemTaker {


    /**
     * Get the stream type
     * @return the stream type
     * @throws ParsingException thrown if there is an error in the extraction
     */
    StreamType getStreamType() throws ParsingException;

    /**
     * Check if the stream is an ad.
     * @return {@code true} if the stream is an ad.
     * @throws ParsingException thrown if there is an error in the extraction
     */
    boolean isAd() throws ParsingException;

    /**
     * Get the stream duration in seconds
     * @return the stream duration in seconds
     * @throws ParsingException thrown if there is an error in the extraction
     */
    long getDuration() throws ParsingException;

    /**
     * Parses the number of views
     * @return the number of views or -1 for live streams
     * @throws ParsingException thrown if there is an error in the extraction
     */
    long getViewCount() throws ParsingException;

    /**
     * Get the uploader name
     * @return the uploader name
     * @throws ParsingException if parsing fails
     */
    String getUploaderName() throws ParsingException;

    String getUploaderUrl() throws ParsingException;

    /**
     * Extract the uploader name
     * @return the uploader name
     * @throws ParsingException thrown if there is an error in the extraction
     */
    String getUploadDate() throws ParsingException;

}
