package menggp.server.dao;

import menggp.server.dao.repo.LocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

/**
 *  Метод реализует CRUD операции для работы с таблицей LOCATION в проекте server-core
 *      Create
 *       Read
 *        Update
 *         Delete
 */
public class CrudMethods {

    private static final Logger log = LoggerFactory.getLogger(CrudMethods.class);
    private List<LocationEntity> all;

    @Autowired
    LocationsRepository locationsRepository;

    public LocationEntity create(String locationString) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setLocationString(locationString);
        return  locationsRepository.save(locationEntity);
    } // end_method

    public void read() {
        all = (List<LocationEntity>) locationsRepository.findAll();

        if (all.size() == 0) {
            log.info("NO RECORDS");
        } else {
            all.stream().forEach(locationEntity -> log.info(locationEntity.toString()));
        }
    } // end_method

    public void update(LocationEntity locationEntity, String locationString) {
        locationEntity.setLocationString(locationString);
        locationsRepository.save(locationEntity);
    } // end_method

    public void delete(LocationEntity locationEntity) {
        locationsRepository.delete(locationEntity);
    } // end_method

    public void flushTable() {
        all = (List<LocationEntity>) locationsRepository.findAll();
        if (all.size() == 0) {
            log.info("NO RECORDS");
        }
        else {
            all.stream().forEach(locationEntity -> delete(locationEntity) );
        }
    } // end_method

    public String readWithReturn(int num) {
        String result="";

        all = (List<LocationEntity>) locationsRepository.findAll();

        // возвращаем строку с N последними записями из таблицы, в обратном порядке, от младшей (нижней) к старшей
        int max = all.size() - 1;
        int min = all.size() - num - 1;

        for( int i=max; i>min ; i--) {
            result = result + all.get(i).getLocationString();
        }

        return result;
    } // end_method

    public int tableSize() {
        all = (List<LocationEntity>) locationsRepository.findAll();
        return all.size();
    } // end_method



} // end_class


