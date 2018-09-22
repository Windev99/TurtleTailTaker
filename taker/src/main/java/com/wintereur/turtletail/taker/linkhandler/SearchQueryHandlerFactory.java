package com.wintereur.turtletail.taker.linkhandler;

import com.wintereur.turtletail.taker.exceptions.ParsingException;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchQueryHandlerFactory extends ListLinkHandlerFactory {

    ///////////////////////////////////
    // To Override
    ///////////////////////////////////

    @Override
    public abstract String getUrl(String querry, List<String> contentFilter, String sortFilter) throws ParsingException;
    public  String getSearchString(String url) { return "";}

    ///////////////////////////////////
    // Logic
    ///////////////////////////////////

    @Override
    public String getId(String url) { return getSearchString(url); }

    @Override
    public SearchQueryHandler fromQuery(String querry,
                                        List<String> contentFilter,
                                        String sortFilter) throws ParsingException {
        return new SearchQueryHandler(super.fromQuery(querry, contentFilter, sortFilter));
    }

    public SearchQueryHandler fromQuery(String querry) throws ParsingException {
        return fromQuery(querry, new ArrayList<String>(0), "");
    }

    /**
     * It's not mandatorry for TurtleTail to handle the Url
     * @param url
     * @return
     */
    @Override
    public boolean onAcceptUrl(String url) { return false; }
}
