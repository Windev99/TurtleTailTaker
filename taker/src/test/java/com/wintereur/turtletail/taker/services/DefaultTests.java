package com.wintereur.turtletail.taker.services;

import com.wintereur.turtletail.taker.InfoItem;
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.stream.StreamInfoItem;

import java.util.List;

import static org.junit.Assert.*;
import static com.wintereur.turtletail.taker.TakerAsserts.*;

public final class DefaultTests {
    public static void defaultTestListOfItems(int expectedServiceId, List<? extends InfoItem> itemsList, List<Throwable> errors) {
        assertTrue("List of items is empty", !itemsList.isEmpty());
        assertFalse("List of items contains a null element", itemsList.contains(null));
        assertEmptyErrors("Errors during stream list extraction", errors);

        for (InfoItem item : itemsList) {
            assertIsSecureUrl(item.getUrl());
            if (item.getThumbnailUrl() != null && !item.getThumbnailUrl().isEmpty()) {
                assertIsSecureUrl(item.getThumbnailUrl());
            }
            assertNotNull("InfoItem type not set: " + item, item.getInfoType());
            assertEquals("Service id doesn't match: " + item, expectedServiceId, item.getServiceId());

            if (item instanceof StreamInfoItem) {
                StreamInfoItem streamInfoItem = (StreamInfoItem) item;
                assertNotEmpty("Uploader name not set: " + item, streamInfoItem.getUploaderName());
                assertNotEmpty("Uploader url not set: " + item, streamInfoItem.getUploaderUrl());
            }
        }
    }

    public static <T extends InfoItem> ListTaker.InfoItemsPage<T> defaultTestRelatedItems(ListTaker<T> taker, int expectedServiceId) throws Exception {
        final ListTaker.InfoItemsPage<T> page = taker.getInitialPage();
        final List<T> itemsList = page.getItems();
        List<Throwable> errors = page.getErrors();

        defaultTestListOfItems(expectedServiceId, itemsList, errors);
        return page;
    }

    public static <T extends InfoItem> ListTaker.InfoItemsPage<T> defaultTestMoreItems(ListTaker<T> taker, int expectedServiceId) throws Exception {
        assertTrue("Doesn't have more items", taker.hasNextPage());
        ListTaker.InfoItemsPage<T> nextPage = taker.getPage(taker.getNextPageUrl());
        final List<T> items = nextPage.getItems();
        assertTrue("Next page is empty", !items.isEmpty());
        assertEmptyErrors("Next page have errors", nextPage.getErrors());

        defaultTestListOfItems(expectedServiceId, nextPage.getItems(), nextPage.getErrors());
        return nextPage;
    }

    public static void defaultTestGetPageInNewTaker(ListTaker<? extends InfoItem> taker, ListTaker<? extends InfoItem> newTaker, int expectedServiceId) throws Exception {
        final String nextPageUrl = taker.getNextPageUrl();

        final ListTaker.InfoItemsPage<? extends InfoItem> page = newTaker.getPage(nextPageUrl);
        defaultTestListOfItems(expectedServiceId, page.getItems(), page.getErrors());
    }
}
