package menggp.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@SpringBootApplication
@ComponentScan({"menggp.server.config","menggp.server.controllers"})
@PropertySource("classpath:db.properties")
public class Application {

    @Autowired
    private Environment env;

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName( env.getProperty("spring.datasource.driver-class-name"));
        driverManagerDataSource.setUrl( env.getProperty("spring.datasource.url") );
        driverManagerDataSource.setUsername( env.getProperty("spring.datasource.username") );
        driverManagerDataSource.setPassword( env.getProperty("spring.datasource.password") );
        return driverManagerDataSource;
    } // end_bean

    // подключаем RestTemplate
    @Bean       // подключение RestTemplate
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    } // end_bean

    // точка входа в приложение
    public static void main(String[] args) throws Throwable {

        /*
           Запускаем web-сервер на порту, отличном от стандартного - 8088,
           для того чтобы одновременно запускать на хосте web-серверы проетак "server-core" и "server-ui"

            // обычный запуск
            //SpringApplication.run(Application.class, args);

         */
        HashMap<String,Object> props = new HashMap<>();
        props.put("server.port",8088);

        new SpringApplicationBuilder(Application.class)
                .properties(props)
                .run(args);


    } // end_main

} //end_class
