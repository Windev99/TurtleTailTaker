package com.wintereur.turtletail.taker;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;
import static com.wintereur.turtletail.taker.TurtleTail.getServiceByUrl;
import static com.wintereur.turtletail.taker.ServiceList.YouTube;

public class TurtleTailTest {
    @Test
    public void getAllServicesTest() throws Exception {
        assertEquals(TurtleTail.getServices().size(), ServiceList.all().size());
    }

    @Test
    public void testAllServicesHaveDifferentId() throws Exception {
        HashSet<Integer> servicesId = new HashSet<>();
        for (StreamingService streamingService : TurtleTail.getServices()) {
            String errorMsg = "There are services with the same id = " + streamingService.getServiceId() + " (current service > " + streamingService.getServiceInfo().getName() + ")";

            assertTrue(errorMsg, servicesId.add(streamingService.getServiceId()));
        }
    }

    @Test
    public void getServiceWithId() throws Exception {
        assertEquals(TurtleTail.getService(YouTube.getServiceId()), YouTube);
    }

    @Test
    public void getServiceWithName() throws Exception {
        assertEquals(TurtleTail.getService(YouTube.getServiceInfo().getName()), YouTube);
    }

    @Test
    public void getServiceWithUrl() throws Exception {
        assertEquals(getServiceByUrl("https://www.youtube.com/watch?v=_r6CgaFNAGg"), YouTube);
        assertEquals(getServiceByUrl("https://www.youtube.com/channel/UCi2bIyFtz-JdI-ou8kaqsqg"), YouTube);
        assertEquals(getServiceByUrl("https://www.youtube.com/playlist?list=PLRqwX-V7Uu6ZiZxtDDRCi6uhfTH4FilpH"), YouTube);


    }

    @Test
    public void getIdWithServiceName() throws Exception {
        assertEquals(TurtleTail.getIdOfService(YouTube.getServiceInfo().getName()), YouTube.getServiceId());
    }

    @Test
    public void getServiceNameWithId() throws Exception {
        assertEquals(TurtleTail.getNameOfService(YouTube.getServiceId()), YouTube.getServiceInfo().getName());
    }
}
