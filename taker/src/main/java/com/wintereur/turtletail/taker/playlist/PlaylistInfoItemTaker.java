package com.wintereur.turtletail.taker.playlist;

import com.wintereur.turtletail.taker.InfoItemTaker;
import com.wintereur.turtletail.taker.exceptions.ParsingException;

public interface PlaylistInfoItemTaker extends InfoItemTaker {

    /**
     * Get the uploader name
     * @return the uploader name
     * @throws ParsingException
     */
    String getUploaderName() throws ParsingException;

    /**
     * Get the number of streams
     * @return the number of streams
     * @throws ParsingException
     */
    long getStreamCount() throws ParsingException;
}
