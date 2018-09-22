package com.wintereur.turtletail.taker;

import com.wintereur.turtletail.taker.exceptions.ExtractionException;

import java.io.IOException;
import java.util.List;

/*
 * Created by Christian Schabesberger on 28.09.16.
 *
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.com>
 * SuggestionTaker.java is part of TurtleTail.
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

public abstract class SuggestionTaker {

    private final int serviceId;

    public SuggestionTaker(int serviceId) {
        this.serviceId = serviceId;
    }

    public abstract List<String> suggestionList(String query, String contentCountry) throws IOException, ExtractionException;

    public int getServiceId() {
        return serviceId;
    }
}
