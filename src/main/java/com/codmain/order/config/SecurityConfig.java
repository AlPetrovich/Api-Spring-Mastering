package com.codmain.order.config;

import com.codmain.order.security.RestAuthenticationEntryPoint;
import com.codmain.order.security.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //spring detecta que es nuestra config. de spring Security
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public TokenAuthenticationFilter createTokenAutenticationFilter(){
        return new TokenAuthenticationFilter();
    }

    @Bean //encriptador de password
    public PasswordEncoder createPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //metodo donde se hace la configuracion
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf()
                    .disable()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .authorizeRequests()//urls pueden ser consumidas
                    .antMatchers(
                            "/login",
                                        "/signup"
                                )
                    .permitAll()
                .anyRequest()
                    .authenticated()
        ;
        http.addFilterBefore(createTokenAutenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
