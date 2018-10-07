package com.wintereur.turtletail.taker;


import com.wintereur.turtletail.taker.services.youtube.YoutubeService;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * A list of supported services.
 */
public final class ServiceList {
    private ServiceList() {
        //no instance
    }

    public static final YoutubeService YouTube;


    private static final List<YoutubeService> SERVICES = unmodifiableList(
            asList(
                    YouTube = new YoutubeService(0)
            ));

    /**
     * Get all the supported services.
     *
     * @return a unmodifiable list of all the supported services
     */
    public static List<YoutubeService> all() {
        return SERVICES;
    }
}
