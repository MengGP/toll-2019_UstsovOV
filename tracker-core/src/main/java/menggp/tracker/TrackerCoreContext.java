package menggp.tracker;

import menggp.tracker.services.GpsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *  Класс контекста
 *      описываем Bean-ы для сервисов и расписания
 */

@Configuration
@EnableScheduling
public class TrackerCoreContext {

    @Bean
    public GpsService schedService() {
        return new GpsService();
    }




}
