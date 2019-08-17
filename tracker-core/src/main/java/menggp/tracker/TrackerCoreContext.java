package menggp.tracker;

import menggp.tracker.services.GpsService;
import menggp.tracker.services.SendService;
import menggp.tracker.services.StoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 *  Устаревший
 *      - функию этого класса взял на себя menggp.tracker.springApp.Application
 *
 *  Класс контекста
 *      описываем Bean-ы для сервисов и расписания
 */

@Configuration
@EnableScheduling
@PropertySource("classpath:/app.properties")
public class TrackerCoreContext {

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



}
