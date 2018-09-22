package com.wintereur.turtletail.taker.services.soundcloud;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.ListTaker;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.kiosk.KioskTaker;
import com.wintereur.turtletail.taker.stream.StreamInfoItem;

import java.util.List;

import static org.junit.Assert.*;
import static com.wintereur.turtletail.taker.ServiceList.SoundCloud;

/**
 * Test for {@link SoundcloudChartsLinkHandlerFactory}
 */
public class SoundcloudChartsTakerTest {

    static KioskTaker taker;

    @BeforeClass
    public static void setUp() throws Exception {
        TurtleTail.init(Downloader.getInstance());
        taker = SoundCloud
                .getKioskList()
                .getTakerById("Top 50", null);
        taker.fetchPage();
    }

    @Test
    public void testGetDownloader() throws Exception {
        assertNotNull(TurtleTail.getDownloader());
    }

    @Ignore
    @Test
    public void testGetName() throws Exception {
        assertEquals(taker.getName(), "Top 50");
    }

    @Test
    public void testId() {
        assertEquals(taker.getId(), "Top 50");
    }

    @Test
    public void testGetStreams() throws Exception {
        ListTaker.InfoItemsPage<StreamInfoItem> page = taker.getInitialPage();
        if(!page.getErrors().isEmpty()) {
            System.err.println("----------");
            List<Throwable> errors = page.getErrors();
            for(Throwable e: errors) {
                e.printStackTrace();
                System.err.println("----------");
            }
        }
        assertTrue("no streams are received",
                !page.getItems().isEmpty()
                        && page.getErrors().isEmpty());
    }

    @Test
    public void testGetStreamsErrors() throws Exception {
        assertTrue("errors during stream list extraction", taker.getInitialPage().getErrors().isEmpty());
    }

    @Test
    public void testHasMoreStreams() throws Exception {
        // Setup the streams
        taker.getInitialPage();
        assertTrue("has more streams", taker.hasNextPage());
    }

    @Test
    public void testGetNextPageUrl() throws Exception {
        assertTrue(taker.hasNextPage());
    }

    @Test
    public void testGetNextPage() throws Exception {
        taker.getInitialPage().getItems();
        assertFalse("taker has next streams", taker.getPage(taker.getNextPageUrl()) == null
                || taker.getPage(taker.getNextPageUrl()).getItems().isEmpty());
    }

    @Test
    public void testGetCleanUrl() throws Exception {
        assertEquals(taker.getUrl(), "https://soundcloud.com/charts/top");
    }
}
