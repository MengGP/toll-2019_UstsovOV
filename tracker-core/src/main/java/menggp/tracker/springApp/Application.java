package menggp.tracker.springApp;

import menggp.tracker.services.GpsService;
import menggp.tracker.services.SendService;
import menggp.tracker.services.StoreService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
@ComponentScan( {"menggp.tracker.springApp","menggp.tracker.services","menggp.tracker.controllers"} )
@Configuration
@EnableScheduling
@PropertySource("classpath:/app.properties")
public class Application {

    // точка входа Spring-boot приложения
    public static void main(String[] args) {
        // запускаем в виде SpringBootApplication - но без поднятия web-cервера - т.к. для реализации функционала он не нужен
        // - т.к. для реализации функционала он не нужен и бужет мешать web-серверу проекта server-core
        new SpringApplicationBuilder(Application.class)
                .web(false) // но это не точно
                .run(args);
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





} // end_class
