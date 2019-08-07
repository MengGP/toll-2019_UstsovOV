package menggp.tracker;

import com.fasterxml.jackson.core.JsonParser;
import com.sun.corba.se.spi.ior.ObjectKey;
import de.micromata.opengis.kml.v_2_2_0.*;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GpsServiceTest {

    @Test // try 1
    public void testKmlTry1() throws Exception {
        System.out.println(" ---> test KML parse test");
        final Kml kml = Kml.unmarshal(new File("D:\\Work\\SoftDev\\ws-java_extend\\Skalniki_Olhinskoe_plateau.kml" ));
        final Document document = (Document)kml.getFeature();
        System.out.println( document.getName() );
        List<Feature> t = document.getFeature();

        for(Object o : t) {
            Folder f = (Folder)o;
            List<Feature> tg = f.getFeature();
            for(Object ftg : tg ) {
                GroundOverlay g = (GroundOverlay) ftg;
                LatLonBox l = g.getLatLonBox();
                System.out.println( l.getNorth() );
                LookAt lk = (LookAt)g.getAbstractView();
                System.out.println( lk.getLatitude() );
            }
        }
    } // end_test

    @Test
    public void testKmlTry2() throws Exception {

        JAXBContext jc = JAXBContext.newInstance( Kml.class );

        // crash in this block
        Unmarshaller u = jc.createUnmarshaller();
        Kml kml = (Kml) u.unmarshal(new File("D:\\Work\\SoftDev\\ws-java_extend\\TrackSample.kml" ) );
        // ------------------------

        Placemark placemark = (Placemark) kml.getFeature();
        Polygon geom = (Polygon) placemark.getGeometry();
        LinearRing linearRing = geom.getOuterBoundaryIs().getLinearRing();
        List<Coordinate> coordinates = linearRing.getCoordinates();
        for ( Coordinate coordinate : coordinates ) {
            System.out.println(coordinate.getLongitude());
            System.out.println(coordinate.getLatitude());
            System.out.println(coordinate.getAltitude());
        } // end_for

    } // end_test

    @Test       // SUCCESFULL
    public void testKmlTry3() throws Exception {
        System.out.println(" > 1 > start test #3");
        // создаем объект KML
        final Kml kml = Kml.unmarshal(new File("D:\\Work\\SoftDev\\ws-java_extend\\TrackSample.kml" ) );
        final Document document = (Document) kml.getFeature();

        List<Feature> placemarks = document.getFeature();
        System.out.println(" > catch array of placemarks > ");

        // цикл по массиву треков в KML-файле
        for (Object sectionOfTrack : placemarks ) {

            // снимаем слой с итератора - приводим к типу Placemark и получаем полу geometry
            Geometry geometry = ((Placemark)sectionOfTrack).getGeometry();

            // получаем из поля geomrtry массив координат - т.е. текущий трек из файла
            List<Coordinate> coordinates = ((LineString)geometry).getCoordinates();

            // цикл по точкам трека
            for (Coordinate pointOfTrack : coordinates ) {
                System.out.println(" -> ");
                System.out.print("\t lat: " + pointOfTrack.getLatitude());
                System.out.print("\t lon: " + pointOfTrack.getLongitude());
                System.out.print("\t class: " + pointOfTrack.getClass());
            } // end_for

        } // end_for

    } // end_test

} // end_class
