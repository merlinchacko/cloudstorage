package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.udacity.jwdnd.course1.cloudstorage.service.AuthenticationService;

import lombok.AllArgsConstructor;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private AuthenticationService authenticationService;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
    {
        authenticationManagerBuilder.authenticationProvider(this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.authorizeRequests()
            .antMatchers("/signup", "/css/**", "/js/**").permitAll()
            .anyRequest().authenticated();

        httpSecurity.formLogin()
            .loginPage("/login")
            .permitAll();

        httpSecurity.formLogin()
            .defaultSuccessUrl("/home", true);

        httpSecurity.logout().logoutUrl("/logout");
    }

}
