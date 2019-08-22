package menggp.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private static  final Logger Log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private DataSource dataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/","/home").authenticated()
                    .antMatchers("/error").authenticated()
                    .antMatchers("/css","/img").permitAll()
                    .antMatchers("/routes","/routes/**").hasAnyRole("CLIENT","MANAGER","ROOT")
                    .antMatchers("/payments","payments/**").hasAnyRole("CLIENT","MANAGER","ROOT")
                    .antMatchers("/registerClient","/registerClient/**").hasAnyRole("MANAGER","ROOT")
                    .antMatchers("/registerManager","/registerManager/**").hasRole("ROOT")
                    .antMatchers("/pointsNumberPage","/requestPoints").hasAnyRole("CLIENT","MANAGER","ROOT")
                    .anyRequest().authenticated()
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
        // пользователи "по умолчанию"

        auth
                .inMemoryAuthentication()
                    .withUser("client").password("client").roles("CLIENT")
                    .and()
                    .withUser("manager").password("manager").roles("MANAGER")
                    .and()
                    .withUser("root").password("root").roles("ROOT");

        // JDBC authentication
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery(getUserQuery())
            .authoritiesByUsernameQuery(getAuthoritiesQuery());
//            .usersByUsernameQuery("select name, password, enabled from userdata where name=?")
//            .authoritiesByUsernameQuery("select name, role from userdata where name=? OR name=name");

    } // end_method


    private String getUserQuery() {
        return "SELECT name as username, password, enabled " +
                "FROM userdata " +
                "WHERE name = ?";
    } // end_method

    private String getAuthoritiesQuery() {
        return "SELECT name as username, role as authority " +
                "FROM userdata " +
                "WHERE name = ?";
    } // end_method


} //end_class
