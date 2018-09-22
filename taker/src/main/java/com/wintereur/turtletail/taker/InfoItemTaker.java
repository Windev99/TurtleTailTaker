package com.wintereur.turtletail.taker;

import com.wintereur.turtletail.taker.exceptions.ParsingException;

public interface InfoItemTaker {
    String getName() throws ParsingException;
    String getUrl() throws ParsingException;
    String getThumbnailUrl() throws ParsingException;
}
