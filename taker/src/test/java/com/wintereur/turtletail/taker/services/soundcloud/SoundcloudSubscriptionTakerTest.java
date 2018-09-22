package com.wintereur.turtletail.taker.services.soundcloud;

import org.junit.BeforeClass;
import org.junit.Test;
import com.wintereur.turtletail.Downloader;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.ServiceList;
import com.wintereur.turtletail.taker.linkhandler.LinkHandlerFactory;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.subscription.SubscriptionTaker;
import com.wintereur.turtletail.taker.subscription.SubscriptionItem;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for {@link SoundcloudSubscriptionTaker}
 */
public class SoundcloudSubscriptionTakerTest {
    private static SoundcloudSubscriptionTaker subscriptionTaker;
    private static LinkHandlerFactory urlHandler;

    @BeforeClass
    public static void setupClass() {
        TurtleTail.init(Downloader.getInstance());
        subscriptionTaker = new SoundcloudSubscriptionTaker(ServiceList.SoundCloud);
        urlHandler = ServiceList.SoundCloud.getChannelLHFactory();
    }

    @Test
    public void testFromChannelUrl() throws Exception {
        testList(subscriptionTaker.fromChannelUrl("https://soundcloud.com/monstercat"));
        testList(subscriptionTaker.fromChannelUrl("http://soundcloud.com/monstercat"));
        testList(subscriptionTaker.fromChannelUrl("soundcloud.com/monstercat"));
        testList(subscriptionTaker.fromChannelUrl("monstercat"));

        //Empty followings user
        testList(subscriptionTaker.fromChannelUrl("some-random-user-184047028"));
    }

    @Test
    public void testInvalidSourceException() {
        List<String> invalidList = Arrays.asList(
                "httttps://invalid.com/user",
                ".com/monstercat",
                "ithinkthatthisuserdontexist",
                "",
                null
        );

        for (String invalidUser : invalidList) {
            try {
                subscriptionTaker.fromChannelUrl(invalidUser);

                fail("didn't throw exception");
            } catch (IOException e) {
                // Ignore it, could be an unstable network on the CI server
            } catch (Exception e) {
                boolean isExpectedException = e instanceof SubscriptionTaker.InvalidSourceException;
                assertTrue(e.getClass().getSimpleName() + " is not the expected exception", isExpectedException);
            }
        }
    }

    private void testList(List<SubscriptionItem> subscriptionItems) throws ParsingException {
        for (SubscriptionItem item : subscriptionItems) {
            assertNotNull(item.getName());
            assertNotNull(item.getUrl());
            assertTrue(urlHandler.acceptUrl(item.getUrl()));
            assertFalse(item.getServiceId() == -1);
        }
    }
}
