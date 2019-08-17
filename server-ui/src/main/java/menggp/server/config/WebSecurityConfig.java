package menggp.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/home").authenticated()
                    .antMatchers("/css","/img").permitAll()
                    .antMatchers("/routes").hasRole("CLIENT")
                    .anyRequest().hasRole("CLIENT")
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    } // end_method

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws  Exception {
        auth
                .inMemoryAuthentication()
                    .withUser("client").password("client").roles("CLIENT");
    } // end_method


} //end_class
