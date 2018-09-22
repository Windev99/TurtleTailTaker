package com.wintereur.turtletail.taker.kiosk;

import com.wintereur.turtletail.taker.TurtleTail;
import com.wintereur.turtletail.taker.StreamingService;
import com.wintereur.turtletail.taker.linkhandler.ListLinkHandlerFactory;
import com.wintereur.turtletail.taker.exceptions.ExtractionException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public  class KioskList {
    public interface KioskTakerFactory {
        KioskTaker createNewKiosk(final StreamingService streamingService,
                                             final String url,
                                             final String kioskId)
            throws ExtractionException, IOException;
    }

    private final int service_id;
    private final HashMap<String, KioskEntry> kioskList = new HashMap<>();
    private String defaultKiosk = null;

    private class KioskEntry {
        public KioskEntry(KioskTakerFactory ef, ListLinkHandlerFactory h) {
            takerFactory = ef;
            handlerFactory = h;
        }
        final KioskTakerFactory takerFactory;
        final ListLinkHandlerFactory handlerFactory;
    }

    public KioskList(int service_id) {
        this.service_id = service_id;
    }

    public void addKioskEntry(KioskTakerFactory takerFactory, ListLinkHandlerFactory handlerFactory, String id)
        throws Exception {
        if(kioskList.get(id) != null) {
            throw new Exception("Kiosk with type " + id + " already exists.");
        }
        kioskList.put(id, new KioskEntry(takerFactory, handlerFactory));
    }

    public void setDefaultKiosk(String kioskType) {
        defaultKiosk = kioskType;
    }

    public KioskTaker getDefaultKioskTaker(String nextPageUrl)
            throws ExtractionException, IOException {
        if(defaultKiosk != null && !defaultKiosk.equals("")) {
            return getTakerById(defaultKiosk, nextPageUrl);
        } else {
            if(!kioskList.isEmpty()) {
                // if not set get any entry
                Object[] keySet = kioskList.keySet().toArray();
                return getTakerById(keySet[0].toString(), nextPageUrl);
            } else {
                return null;
            }
        }
    }

    public String getDefaultKioskId() {
        return defaultKiosk;
    }

    public KioskTaker getTakerById(String kioskId, String nextPageUrl)
            throws ExtractionException, IOException {
        KioskEntry ke = kioskList.get(kioskId);
        if(ke == null) {
            throw new ExtractionException("No kiosk found with the type: " + kioskId);
        } else {
            return ke.takerFactory.createNewKiosk(TurtleTail.getService(service_id),
                    ke.handlerFactory.fromId(kioskId).getUrl(), kioskId);
        }
    }

    public Set<String> getAvailableKiosks() {
        return kioskList.keySet();
    }

    public KioskTaker getTakerByUrl(String url, String nextPageUrl)
            throws ExtractionException, IOException {
        for(Map.Entry<String, KioskEntry> e : kioskList.entrySet()) {
            KioskEntry ke = e.getValue();
            if(ke.handlerFactory.acceptUrl(url)) {
                return getTakerById(e.getKey(), nextPageUrl);
            }
        }
        throw new ExtractionException("Could not find a kiosk that fits to the url: " + url);
    }

    public ListLinkHandlerFactory getListLinkHandlerFactoryByType(String type) {
        return kioskList.get(type).handlerFactory;
    }
}
