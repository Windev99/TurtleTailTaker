package com.wintereur.turtletail.taker.services.soundcloud;

import com.grack.nanojson.JsonObject;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.stream.StreamInfoItemTaker;
import com.wintereur.turtletail.taker.stream.StreamType;

import static com.wintereur.turtletail.taker.utils.Utils.replaceHttpWithHttps;

public class SoundcloudStreamInfoItemTaker implements StreamInfoItemTaker {

    protected final JsonObject itemObject;

    public SoundcloudStreamInfoItemTaker(JsonObject itemObject) {
        this.itemObject = itemObject;
    }

    @Override
    public String getUrl() {
        return replaceHttpWithHttps(itemObject.getString("permalink_url"));
    }

    @Override
    public String getName() {
        return itemObject.getString("title");
    }

    @Override
    public long getDuration() {
        return itemObject.getNumber("duration", 0).longValue() / 1000L;
    }

    @Override
    public String getUploaderName() {
        return itemObject.getObject("user").getString("username");
    }

    @Override
    public String getUploaderUrl() {
        return replaceHttpWithHttps(itemObject.getObject("user").getString("permalink_url"));
    }

    @Override
    public String getUploadDate() throws ParsingException {
        return SoundcloudParsingHelper.toDateString(itemObject.getString("created_at"));
    }

    @Override
    public long getViewCount() {
        return itemObject.getNumber("playback_count", 0).longValue();
    }

    @Override
    public String getThumbnailUrl() {
        return itemObject.getString("artwork_url");
    }

    @Override
    public StreamType getStreamType() {
        return StreamType.AUDIO_STREAM;
    }

    @Override
    public boolean isAd() {
        return false;
    }
}
