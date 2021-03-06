package com.wintereur.turtletail.taker.utils;

import com.wintereur.turtletail.taker.Downloader;
import com.wintereur.turtletail.taker.MediaFormat;
import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.exceptions.ParsingException;
import com.wintereur.turtletail.taker.exceptions.ReCaptchaException;
import com.wintereur.turtletail.taker.services.youtube.ItagItem;
import com.wintereur.turtletail.taker.stream.AudioStream;
import com.wintereur.turtletail.taker.stream.Stream;
import com.wintereur.turtletail.taker.stream.StreamInfo;
import com.wintereur.turtletail.taker.stream.VideoStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by Christian Schabesberger on 02.02.16.
 *
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.com>
 * DashMpdParser.java is part of TurtleTail.
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

public class DashMpdParser {

    private DashMpdParser() {
    }

    public static class DashMpdParsingException extends ParsingException {
        DashMpdParsingException(String message, Exception e) {
            super(message, e);
        }
    }

    public static class ParserResult {
        private final List<VideoStream> videoStreams;
        private final List<AudioStream> audioStreams;
        private final List<VideoStream> videoOnlyStreams;

        public ParserResult(List<VideoStream> videoStreams, List<AudioStream> audioStreams, List<VideoStream> videoOnlyStreams) {
            this.videoStreams = videoStreams;
            this.audioStreams = audioStreams;
            this.videoOnlyStreams = videoOnlyStreams;
        }

        public List<VideoStream> getVideoStreams() {
            return videoStreams;
        }

        public List<AudioStream> getAudioStreams() {
            return audioStreams;
        }

        public List<VideoStream> getVideoOnlyStreams() {
            return videoOnlyStreams;
        }
    }

    /**
     * Will try to download (using {@link StreamInfo#dashMpdUrl}) and parse the dash manifest,
     * then it will search for any stream that the ItagItem has (by the id).
     * <p>
     * It has video, video only and audio streams and will only add to the list if it don't
     * find a similar stream in the respective lists (calling {@link Stream#equalStats}).
     *
     * Info about dash MPD can be found here
     * @see <a href="https://www.brendanlong.com/the-structure-of-an-mpeg-dash-mpd.html">www.brendanlog.com</a>
     *
     * @param streamInfo where the parsed streams will be added
     */
    public static ParserResult getStreams(final StreamInfo streamInfo) throws DashMpdParsingException, ReCaptchaException {
        String dashDoc;
        Downloader downloader = TurtleTail.getDownloader();
        try {
            dashDoc = downloader.download(streamInfo.getDashMpdUrl());
        } catch (IOException ioe) {
            throw new DashMpdParsingException("Could not get dash mpd: " + streamInfo.getDashMpdUrl(), ioe);
        } catch (ReCaptchaException e) {
            throw new ReCaptchaException("reCaptcha Challenge needed");
        }

        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final InputStream stream = new ByteArrayInputStream(dashDoc.getBytes());

            final Document doc = builder.parse(stream);
            final NodeList representationList = doc.getElementsByTagName("Representation");

            final List<VideoStream> videoStreams = new ArrayList<>();
            final List<AudioStream> audioStreams = new ArrayList<>();
            final List<VideoStream> videoOnlyStreams = new ArrayList<>();

            for (int i = 0; i < representationList.getLength(); i++) {
                final Element representation = (Element) representationList.item(i);
                try {
                    final String mimeType = ((Element) representation.getParentNode()).getAttribute("mimeType");
                    final String id = representation.getAttribute("id");
                    final String url = representation.getElementsByTagName("BaseURL").item(0).getTextContent();
                    final ItagItem itag = ItagItem.getItag(Integer.parseInt(id));
                    final Node segmentationList = representation.getElementsByTagName("SegmentList").item(0);

                    // if SegmentList is not null this means that BaseUrl is not representing the url to the stream.
                    // instead we need to add the "media=" value from the <SegementURL/> tags inside the <SegmentList/>
                    // tag in order to get a full working url. However each of these is just pointing to a part of the
                    // video, so we can not return a URL with a working stream here.
                    // We decided not to ignore such streams for the moment.
                    if (itag != null && segmentationList == null) {
                        final MediaFormat mediaFormat = MediaFormat.getFromMimeType(mimeType);

                        if (itag.itagType.equals(ItagItem.ItagType.AUDIO)) {
                            final AudioStream audioStream = new AudioStream(url, mediaFormat, itag.avgBitrate);

                            if (!Stream.containSimilarStream(audioStream, streamInfo.getAudioStreams())) {
                                audioStreams.add(audioStream);
                            }
                        } else {
                            boolean isVideoOnly = itag.itagType.equals(ItagItem.ItagType.VIDEO_ONLY);
                            final VideoStream videoStream = new VideoStream(url, mediaFormat, itag.resolutionString, isVideoOnly);

                            if (isVideoOnly) {
                                if (!Stream.containSimilarStream(videoStream, streamInfo.getVideoOnlyStreams())) {
                                    streamInfo.getVideoOnlyStreams().add(videoStream);
                                    videoOnlyStreams.add(videoStream);
                                }
                            } else if (!Stream.containSimilarStream(videoStream, streamInfo.getVideoStreams())) {
                                videoStreams.add(videoStream);
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            }
            return new ParserResult(videoStreams, audioStreams, videoOnlyStreams);
        } catch (Exception e) {
            throw new DashMpdParsingException("Could not parse Dash mpd", e);
        }
    }
}
