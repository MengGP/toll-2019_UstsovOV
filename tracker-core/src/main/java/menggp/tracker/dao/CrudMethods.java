package menggp.tracker.dao;

import menggp.tracker.dao.repo.GpsDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 *  Метод реализует CRUD операции для работы с DB в проекте tracker-core
 *      Create
 *       Read
 *        Update
 *         Delete
 */
public class CrudMethods {

    private static final Logger log = LoggerFactory.getLogger(CrudMethods.class);
    private List<GpsData> all;

    @Autowired
    GpsDataRepository gpsDataRepository;

    public GpsData create(double lat, double lon, double azimuth, int instantSpeed, long time, String autoID) {
        GpsData gpsData = new GpsData();

        gpsData.setLat(lat);
        gpsData.setLon(lon);
        gpsData.setAzimuth(azimuth);
        gpsData.setInstantSpeed(instantSpeed);
        gpsData.setTime(time);
        gpsData.setAutoID(autoID);

        return gpsDataRepository.save(gpsData);
    } // end_method

    public void read() {
        all = (List<GpsData>) gpsDataRepository.findAll();

        if (all.size() == 0) {
            log.info("NO RECORDS");
        } else {
            all.stream().forEach(gpsData -> log.info(gpsData.toString()));
        }
    } // end_method

    public void update(GpsData gpsData, double lat, double lon, double azimuth, int instantSpeed, long time, String autoID) {

        gpsData.setLat(lat);
        gpsData.setLat(lon);
        gpsData.setAzimuth(azimuth);
        gpsData.setInstantSpeed(instantSpeed);
        gpsData.setTime(time);
        gpsData.setAutoID(autoID);

        gpsDataRepository.save(gpsData);
    } // end_method

    public void delete(GpsData gpsData) {
        gpsDataRepository.delete(gpsData);
    } // end_method

    public void flushTable() {
        all = (List<GpsData>) gpsDataRepository.findAll();
        if (all.size() == 0) {
            log.info("NO RECORDS");
        }
        else {
            all.stream().forEach(gpsData -> delete(gpsData) );// log.info(GpsData.toString()));
        }
    } // end_method

} // end_class


