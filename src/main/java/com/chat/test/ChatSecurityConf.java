package com.chat.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ChatSecurityConf {

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
            .authorizeHttpRequests()
            .antMatchers("/", "/favicon.ico").permitAll()
            .anyRequest().authenticated()
            .and()
            .logout()
            .and().csrf().disable()
            ;

      return http.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }
}