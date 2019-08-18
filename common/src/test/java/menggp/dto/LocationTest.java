package menggp.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {

    private double lat=60;
    private double lon=40;
    private double azimuth=180;
    private int instantSpeed=60;
    private String autoId="test_autoId";
    private long time=1566029503251L;

    @Test
    public void setLat() {
        Location testLocation = new Location();
        testLocation.setLat(lat);
        assertEquals("setLat check",60,testLocation.getLat(),0);
    }

    @Test
    public void getAzimuth() {
        Location testLocation = new Location();
        testLocation.setAzimuth(azimuth);
        assertEquals("getAzimuth check",180,testLocation.getAzimuth(),0);
    }

    @Test
    public void setAzimuth() {
        Location testLocation = new Location();
        testLocation.setAzimuth(azimuth);
        assertEquals("setAzimuth check",180,testLocation.getAzimuth(),0);
    }

    @Test
    public void getInstantSpeed() {
        Location testLocation = new Location();
        testLocation.setInstantSpeed(instantSpeed);
        assertEquals("getInstantSpeed check",60,testLocation.getInstantSpeed());
    }

    @Test
    public void setInstantSpeed() {
        Location testLocation = new Location();
        testLocation.setInstantSpeed(instantSpeed);
        assertEquals("setInstantSpeed check",60,testLocation.getInstantSpeed());
    }

    @Test
    public void getLat() {
        Location testLocation = new Location();
        testLocation.setLat(lat);
        assertEquals("getLat check",60,testLocation.getLat(),0);
    }

    @Test
    public void getLon() {
        Location testLocation = new Location();
        testLocation.setLon(lon);
        assertEquals("getLon check",40,testLocation.getLon(),0);
    }

    @Test
    public void setLon() {
        Location testLocation = new Location();
        testLocation.setLon(lon);
        assertEquals("setLon check",40,testLocation.getLon(),0);
    }

    @Test
    public void getAutoId() {
        Location testLocation = new Location();
        testLocation.setAutoId(autoId);
        assertEquals("test_autoId",testLocation.getAutoId());
    }

    @Test
    public void setAutoId() {
        Location testLocation = new Location();
        testLocation.setAutoId(autoId);
        assertEquals("test_autoId",testLocation.getAutoId());
    }

    @Test
    public void setTime() {
        Location testLocation = new Location();
        testLocation.setTime(time);
        assertEquals("setTime check",1566029503251L,testLocation.getTime());
    }

    @Test
    public void getTime() {
        Location testLocation = new Location();
        testLocation.setTime(time);
        assertEquals("getTime check",1566029503251L,testLocation.getTime());
    }

    @Test
    public void toJson() throws JsonProcessingException {
        Location testLocation = new Location();
        testLocation.setLat(lat);
        testLocation.setLon(lon);
        testLocation.setAutoId(autoId);
        testLocation.setTime(time);
        testLocation.setAzimuth(azimuth);
        testLocation.setInstantSpeed(instantSpeed);
        String testLocationJSON = testLocation.toJson();
        assertEquals(
                "{\"lat\":60.0,\"lon\":40.0,\"autoId\":\"test_autoId\",\"time\":1566029503251,\"azimuth\":180.0,\"instantSpeed\":60}",
                testLocationJSON);
    }
}