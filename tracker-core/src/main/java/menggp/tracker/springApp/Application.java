package menggp.tracker.springApp;

import menggp.tracker.dao.GpsData;
import menggp.tracker.dao.CrudMethods;
import menggp.tracker.services.GpsService;
import menggp.tracker.services.SendService;
import menggp.tracker.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

/**
 *      Класс содержащий точку входа Spring-boot приложения
 *
 *      - т.к. заменяет фунуионал классов TrackerCoreContext и TrackerCoreMain
 *      поаналогии, переносим сюда beans и необходимые аннотации
 *
 */

@SpringBootApplication
@ComponentScan( {"menggp.tracker.springApp","menggp.tracker.services","menggp.tracker.controllers","menggp.tracker.dao"} )
@Configuration
@EnableScheduling
@PropertySource("classpath:/app.properties")
@EnableJpaRepositories("menggp.tracker.dao")           // for DB
@EntityScan(basePackageClasses =  GpsData.class)       // for DB
public class Application implements CommandLineRunner {

    // точка входа Spring-boot приложения
    public static void main(String[] args) {
        // запускаем в виде SpringBootApplication - но без поднятия web-cервера - т.к. для реализации функционала он не нужен
        // - т.к. для реализации функционала он не нужен и бужет мешать web-серверу проекта server-core
        new SpringApplicationBuilder(Application.class)
                .web(false)
                .run(args);
        // обычный запуск
        //SpringApplication.run(Application.class, args);
    } // end_main

    @Bean       // сервис генерации данных GPS
    public GpsService gpsService() {
        return new GpsService();
    }

    @Bean       // сервис хранения собираемынх данных GPS
    public StoreService storeService() {
        return new StoreService();
    }

    @Bean       // сервис отправки местоположения ТС - в лог
    public SendService sendService() {
        return new SendService();
    }

    @Bean       // настройка многопоточности
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler-");
        scheduler.setPoolSize(20);
        return scheduler;
    }

    @Bean       // подключение RestTemplate
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    // подключение application.properties для работы с DB
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public CrudMethods crudMethods () {
        return new CrudMethods();
    } // end_bean

//    @Autowired
//    private CrudMethods crudMethods;

    @Override
    public void run(String... args) throws Exception {
        /*
        // очистка таблицы при старте приложения
        crudMethods.flushTable();
        crudMethods.create(1,2,3,4,5,"str");
        crudMethods.read();
        */

    } // end_method





} // end_class
