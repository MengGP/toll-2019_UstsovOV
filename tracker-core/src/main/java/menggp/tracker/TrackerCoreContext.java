package menggp.tracker;

import menggp.tracker.services.GpsService;
import menggp.tracker.services.StoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *  Класс контекста
 *      описываем Bean-ы для сервисов и расписания
 */

@Configuration
@EnableScheduling
@PropertySource("classpath:/app.properties")
public class TrackerCoreContext {

    @Bean
    public GpsService gpsService() {
        return new GpsService();
    }

    @Bean
    public StoreService storeService() {
        return new StoreService();
    }



}
