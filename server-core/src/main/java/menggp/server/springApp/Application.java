package menggp.server.springApp;

import menggp.server.dao.CrudMethods;
import menggp.server.dao.CrudUserMethods;
import menggp.server.dao.LocationEntity;
import menggp.server.services.WriteLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
@ComponentScan({"menggp.server.springApp","menggp.server.services","menggp.server.controllers","menggp.server.dao"})
@EnableJpaRepositories("menggp.server.dao")                   // for DB
@EntityScan(basePackageClasses =  LocationEntity.class)       // for DB
public class Application implements CommandLineRunner  {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    } // end_main

    @Bean   // сервис записи GPS данных
    public WriteLocationService writeLocationService() {
        return new WriteLocationService();
    } //end_bean

    @Bean // подключение application.properties для работы с DB
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

    @Autowired
    CrudMethods crudMethods;

    @Autowired
    CrudUserMethods crudUserMethods;

    @Override
    public void run(String... args) throws Exception{
        crudMethods.flushTable();
    } // end_method

} // end_class
