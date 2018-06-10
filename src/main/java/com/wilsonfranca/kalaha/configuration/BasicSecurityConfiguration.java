package com.wilsonfranca.kalaha.configuration;

import com.wilsonfranca.kalaha.auth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                		.antMatchers("/css/**").permitAll()
                		.antMatchers("/js/**").permitAll()
                		.antMatchers("/webjars/**").permitAll()
                    .antMatchers("/").permitAll()
                    .antMatchers("/signup.html").permitAll()
                    .antMatchers("/games.html").hasAnyRole("PLAYER")
                .and()
                .formLogin()
                .loginPage("/login.html")
                .failureUrl("/login-error.html")
                .defaultSuccessUrl("/games.html")
                .and()
                .logout()
                .permitAll();


    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.customUserDetailsService);
    }

}
