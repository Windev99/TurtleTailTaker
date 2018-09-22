package com.wintereur.turtletail.taker.utils;

import com.wintereur.turtletail.taker.Info;
import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.InfoItemsCollector;
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.ListTaker.InfoItemsPage;
import com.wintereur.turtletail.taker.stream.StreamTaker;
import com.wintereur.turtletail.taker.stream.StreamInfo;

import java.util.Collections;
import java.util.List;

public class TakerHelper {
    private TakerHelper() {}

    public static <T extends InfoItem> InfoItemsPage<T> getItemsPageOrLogError(Info info, ListTaker<T> taker) {
        try {
            InfoItemsPage<T> page = taker.getInitialPage();
            info.addAllErrors(page.getErrors());

            return page;
        } catch (Exception e) {
            info.addError(e);
            return InfoItemsPage.emptyPage();
        }
    }


    public static List<InfoItem> getRelatedVideosOrLogError(StreamInfo info, StreamTaker taker) {
        try {
            InfoItemsCollector<? extends InfoItem, ?> collector = taker.getRelatedVideos();
            info.addAllErrors(collector.getErrors());

            //noinspection unchecked
            return (List<InfoItem>) collector.getItems();
        } catch (Exception e) {
            info.addError(e);
            return Collections.emptyList();
        }
    }
}
