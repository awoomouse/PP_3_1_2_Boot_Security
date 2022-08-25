package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.kata.spring.boot_security.demo.service.UserDetailsImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsImpl userDetails;
    private final MyPasswordEncoder myPasswordEncoder;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetailsImpl userDetails, MyPasswordEncoder myPasswordEncoder) {
        this.successUserHandler = successUserHandler;
        this.userDetails = userDetails;
        this.myPasswordEncoder = myPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers("/user", "/api/user")
                .hasRole("USER")
                .antMatchers("/admin/**", "/api/**")
                .hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(myPasswordEncoder.getPasswordEncoder());
    }
}