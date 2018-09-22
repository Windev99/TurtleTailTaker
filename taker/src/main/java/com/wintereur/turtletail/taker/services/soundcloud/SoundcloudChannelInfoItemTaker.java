package com.wintereur.turtletail.taker.services.soundcloud;

import com.grack.nanojson.JsonObject;
import com.wintereur.turtletail.taker.channel.ChannelInfoItemTaker;

import static com.wintereur.turtletail.taker.utils.Utils.replaceHttpWithHttps;

public class SoundcloudChannelInfoItemTaker implements ChannelInfoItemTaker {
    private final JsonObject itemObject;

    public SoundcloudChannelInfoItemTaker(JsonObject itemObject) {
        this.itemObject = itemObject;
    }

    @Override
    public String getName() {
        return itemObject.getString("username");
    }

    @Override
    public String getUrl() {
        return replaceHttpWithHttps(itemObject.getString("permalink_url"));
    }

    @Override
    public String getThumbnailUrl() {
        return itemObject.getString("avatar_url", "");
    }

    @Override
    public long getSubscriberCount() {
        return itemObject.getNumber("followers_count", 0).longValue();
    }

    @Override
    public long getStreamCount() {
        return itemObject.getNumber("track_count", 0).longValue();
    }

    @Override
    public String getDescription() {
        return itemObject.getString("description", "");
    }
}
