package com.wintereur.turtletail.taker.search;

import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.StreamingService;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.linkhandler.SearchQueryHandler;

public abstract class SearchTaker extends ListTaker<InfoItem> {

    public static class NothingFoundException extends ExtractionException {
        public NothingFoundException(String message) {
            super(message);
        }
    }

    private final InfoItemsSearchCollector collector;
    private final String contentCountry;

    public SearchTaker(StreamingService service, SearchQueryHandler urlIdHandler, String contentCountry) {
        super(service, urlIdHandler);
        collector = new InfoItemsSearchCollector(service.getServiceId());
        this.contentCountry = contentCountry;
    }

    public String getSearchString() {
        return getUIHandler().getSearchString();
    }

    public abstract String getSearchSuggestion() throws ParsingException;

    protected InfoItemsSearchCollector getInfoItemSearchCollector() {
        return collector;
    }

    @Override
    public SearchQueryHandler getUIHandler() {
        return (SearchQueryHandler) super.getUIHandler();
    }

    @Override
    public String getName() {
        return getUIHandler().getSearchString();
    }

    protected String getContentCountry() {
        return contentCountry;
    }
}
