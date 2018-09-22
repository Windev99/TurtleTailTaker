package com.wintereur.turtletail.taker.search;

import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.ListInfo;
import com.wintereur.turtletail.taker.StreamingService;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;
import com.wintereur.turtletail.taker.linkhandler.SearchQueryHandler;
import com.wintereur.turtletail.taker.utils.TakerHelper;

import java.io.IOException;


public class SearchInfo extends ListInfo<InfoItem> {

    private String searchString;
    private String searchSuggestion;

    public SearchInfo(int serviceId,
                      SearchQueryHandler qIHandler,
                      String searchString) {
        super(serviceId, qIHandler, "Search");
        this.searchString = searchString;
    }


    public static SearchInfo getInfo(StreamingService service, SearchQueryHandler searchQuery, String contentCountry) throws ExtractionException, IOException {
        SearchTaker taker = service.getSearchTaker(searchQuery, contentCountry);
        taker.fetchPage();
        return getInfo(taker);
    }

    public static SearchInfo getInfo(SearchTaker taker) throws ExtractionException, IOException {
        final SearchInfo info = new SearchInfo(
                taker.getServiceId(),
                taker.getUIHandler(),
                taker.getSearchString());

        try {
            info.searchSuggestion = taker.getSearchSuggestion();
        } catch (Exception e) {
            info.addError(e);
        }

        ListTaker.InfoItemsPage<InfoItem> page = TakerHelper.getItemsPageOrLogError(info, taker);
        info.setRelatedItems(page.getItems());
        info.setNextPageUrl(page.getNextPageUrl());

        return info;
    }


    public static ListTaker.InfoItemsPage<InfoItem> getMoreItems(StreamingService service,
                                                                     SearchQueryHandler query,
                                                                     String contentCountry,
                                                                     String pageUrl)
            throws IOException, ExtractionException {
        return service.getSearchTaker(query, contentCountry).getPage(pageUrl);
    }

    // Getter
    public String getSearchString() {
        return searchString;
    }

    public String getSearchSuggestion() {
        return searchSuggestion;
    }
}
