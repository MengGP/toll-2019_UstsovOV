package menggp.server.springApp;

import menggp.server.dao.CrudMethods;
import menggp.server.dao.CrudUserMethods;
import menggp.server.dao.LocationEntity;
import menggp.server.dao.repo.LocationsRepository;
import menggp.server.services.WriteLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootApplication
@ComponentScan({"menggp.server.springApp","menggp.server.services","menggp.server.controllers","menggp.server.dao"})
@EnableJpaRepositories("menggp.server.dao")                   // for DB
@EntityScan(basePackageClasses =  LocationEntity.class)       // for DB
public class Application implements CommandLineRunner  {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
//    private List<LocationEntity> all;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    } // end_main

    @Bean   // сервис записи GPS данных
    public WriteLocationService writeLocationService() {
        return new WriteLocationService();
    } //end_bean

    // подключение application.properties для работы с DB
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public CrudMethods crudMethods () {
        return new CrudMethods();
    } // end_bean

    @Bean
    public CrudUserMethods crudUserMethods() {
        return new CrudUserMethods();
    } // end_bean

//    @Autowired
//    LocationsRepository locationsRepository;

    @Autowired
    CrudMethods crudMethods;

    @Autowired
    CrudUserMethods crudUserMethods;

    @Override
    public void run(String... args) throws Exception{

        crudMethods.flushTable();

        /*
        crudUserMethods.flushTable();
        crudUserMethods.create("admin", "admin", "ROLE_ROOT",true);
        crudUserMethods().read();
        */

        /*
        crudMethods.read();

        LocationEntity loc1 = crudMethods.create("location string #1"); //create("location string #1");
        LocationEntity loc2 = crudMethods.create("location string #2");
        LocationEntity loc3 = crudMethods.create("location string #3");
        log.info("=========== after create");
        crudMethods.read();

        crudMethods.update(loc1, "Location #1 - test pass");
        crudMethods.update(loc2, "Location #2 - test pass");
        crudMethods.update(loc3, "Location #3 - test pass");
        log.info("=========== after update");
        crudMethods.read();

        crudMethods.delete(loc1);
        log.info("=========== after delete 1");
        crudMethods.read();


        crudMethods.delete(loc2);
        log.info("=========== after delete 2");
        crudMethods.read();

        crudMethods.delete(loc3);
        log.info("=========== after delete 3");
        crudMethods.read();
         */

    } // end_method

    /*
    private LocationEntity create(String locationString) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setLocationString(locationString);
        return  locationsRepository.save(locationEntity);
    } // end_method

    private void read() {
        all = (List<LocationEntity>) locationsRepository.findAll();

        if (all.size() == 0) {
            log.info("NO RECORDS");
        } else {
            all.stream().forEach(locationEntity -> log.info(locationEntity.toString()));
        }
    } // end_method

    private void update(LocationEntity locationEntity, String locationString) {
        locationEntity.setLocationString(locationString);
        locationsRepository.save(locationEntity);
    } // end_method

    private void delete(LocationEntity locationEntity) {
        locationsRepository.delete(locationEntity);
    } // end_method
     */


} // end_class
