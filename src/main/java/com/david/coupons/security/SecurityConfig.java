package com.david.coupons.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtRequestFilter jwtRequestFilter;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(final AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.userDetailsService(userDetailsService);
    }



    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/companies/**").hasAuthority("Company")
                .antMatchers(HttpMethod.POST, "/companies").hasAuthority("Company")
                .antMatchers(HttpMethod.PUT, "/companies/**").hasAuthority("Company")
                .antMatchers(HttpMethod.DELETE, "/companies/**").hasAuthority("Company")
                .antMatchers(HttpMethod.GET, "/customers/**").hasAuthority("Customer")
                .antMatchers(HttpMethod.POST, "/customers/**").hasAuthority("Customer")
                .antMatchers(HttpMethod.PUT, "/customers/**").hasAuthority("Customer")
                .antMatchers(HttpMethod.DELETE, "/customers/**").hasAuthority("Customer")
                .antMatchers(HttpMethod.GET, "/admin/**").hasAuthority("Admin")
                .antMatchers(HttpMethod.POST, "/admin/**").hasAuthority("Admin")
                .antMatchers(HttpMethod.PUT, "/admin/**").hasAuthority("Admin")
                .antMatchers(HttpMethod.DELETE, "/admin/**").hasAuthority("Admin")
                .and()
                .csrf().disable()
                .formLogin().disable();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);// Adds out filter to the filter chain
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
