package menggp.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {

    private double lat=60;
    private double lon=40;
    private double azimuth=180;
    private int instantSpeed=60;
    private String autoId="test_autoId";
    private long time=1566029503251L;

    @Test
    public void setLat() {
        Point testPoint = new Point();
        testPoint.setLat(lat);
        assertEquals("setLat check",60,testPoint.getLat(),0);
    }

    @Test
    public void getAzimuth() {
        Point testPoint = new Point();
        testPoint.setAzimuth(azimuth);
        assertEquals("getAzimuth check",180,testPoint.getAzimuth(),0);
    }

    @Test
    public void setAzimuth() {
        Point testPoint = new Point();
        testPoint.setAzimuth(azimuth);
        assertEquals("setAzimuth check",180,testPoint.getAzimuth(),0);
    }

    @Test
    public void getInstantSpeed() {
        Point testPoint = new Point();
        testPoint.setInstantSpeed(instantSpeed);
        assertEquals("getInstantSpeed check",60,testPoint.getInstantSpeed());
    }

    @Test
    public void setInstantSpeed() {
        Point testPoint = new Point();
        testPoint.setInstantSpeed(instantSpeed);
        assertEquals("setInstantSpeed check",60,testPoint.getInstantSpeed());
    }

    @Test
    public void getLat() {
        Point testPoint = new Point();
        testPoint.setLat(lat);
        assertEquals("getLat check",60,testPoint.getLat(),0);
    }

    @Test
    public void getLon() {
        Point testPoint = new Point();
        testPoint.setLon(lon);
        assertEquals("getLon check",40,testPoint.getLon(),0);
    }

    @Test
    public void setLon() {
        Point testPoint = new Point();
        testPoint.setLon(lon);
        assertEquals("setLon check",40,testPoint.getLon(),0);
    }

    @Test
    public void getAutoId() {
        Point testPoint = new Point();
        testPoint.setAutoId(autoId);
        assertEquals("test_autoId",testPoint.getAutoId());
    }

    @Test
    public void setAutoId() {
        Point testPoint = new Point();
        testPoint.setAutoId(autoId);
        assertEquals("test_autoId",testPoint.getAutoId());
    }

    @Test
    public void setTime() {
        Point testPoint = new Point();
        testPoint.setTime(time);
        assertEquals("setTime check",1566029503251L,testPoint.getTime());
    }

    @Test
    public void getTime() {
        Point testPoint = new Point();
        testPoint.setTime(time);
        assertEquals("getTime check",1566029503251L,testPoint.getTime());
    }

    @Test
    public void toJson() throws JsonProcessingException {
        Point testPoint = new Point();
        testPoint.setLat(lat);
        testPoint.setLon(lon);
        testPoint.setAutoId(autoId);
        testPoint.setTime(time);
        testPoint.setAzimuth(azimuth);
        testPoint.setInstantSpeed(instantSpeed);
        String testPointJSON = testPoint.toJson();
        assertEquals(
                "{\"lat\":60.0,\"lon\":40.0,\"autoId\":\"test_autoId\",\"time\":1566029503251,\"azimuth\":180.0,\"instantSpeed\":60}",
                testPointJSON);
    }

    @Test
    public void toString1() {
        Point testPoint = new Point();
        testPoint.setLat(lat);
        testPoint.setLon(lon);
        testPoint.setAutoId(autoId);
        testPoint.setTime(time);
        testPoint.setAzimuth(azimuth);
        testPoint.setInstantSpeed(instantSpeed);
        String testPointString = testPoint.toString();
        assertEquals(
                "Point{lat=60.0, lon=40.0, autoId='test_autoId', time=1566029503251}",
                testPointString);
    }

} // end_tests