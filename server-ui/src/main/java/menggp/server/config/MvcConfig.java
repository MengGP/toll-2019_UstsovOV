package menggp.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/routes").setViewName("routes");
        registry.addViewController("/payments").setViewName("payments");
        registry.addViewController("/registerClient").setViewName("registerClient");
        registry.addViewController("/registerManager").setViewName("registerManager");
        registry.addViewController("/createUserPage").setViewName("createUserPage");
        registry.addViewController("/updateUserPage").setViewName("updateUserPage");
        registry.addViewController("/createClientPage").setViewName("createClientPage");
        registry.addViewController("/updateClientPage").setViewName("updateCientPage");
        registry.addViewController("/createManagerPage").setViewName("createManagerPage");
        registry.addViewController("/updateManagerPage").setViewName("updateManagerPage");



        registry.addViewController("/login").setViewName("login");
    } // end_method

} // end_class
