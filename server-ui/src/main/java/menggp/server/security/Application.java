package menggp.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.HashMap;

@SpringBootApplication
@ComponentScan("menggp.server.config")
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
    }


    // точка входа в приложение
    public static void main(String[] args) throws Throwable {

        HashMap<String,Object> props = new HashMap<>();
        props.put("server.port",8088);

        //SpringApplication.run(Application.class, args);
        // запускаем в виде SpringBootApplication - но без поднятия web-cервера - т.к. для реализации функционала он не нужен
        // - т.к. для реализации функционала он не нужен и бужет мешать web-серверу проекта server-core
        new SpringApplicationBuilder(Application.class)
                //.web(false) // но это не точно
                .properties(props)
                .run(args);
        // обычный запуск
        //SpringApplication.run(Application.class, args);

    } // end_main

} //end_class
