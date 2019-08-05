package menggp.dto;

import org.junit.Test;

/**
 *  Тетовый класс для класса Location
 */

public class LocationTest {

    @Test
    public void createLocationObj() throws Exception {

        Location testLocation = new Location();

        testLocation.setLat( 33.33 );
        System.out.println( testLocation.getLat() );

        System.out.println(" ---> end test");



    } // end_method


} // end_class
