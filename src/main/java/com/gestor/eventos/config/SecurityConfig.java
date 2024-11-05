package com.gestor.eventos.config;
import com.gestor.eventos.Security.JwtAuthenticationEntryPoint;
import com.gestor.eventos.Security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    };

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/eventos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/clientes/**").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/grupos/**").hasAnyRole("CLIENTE", "GRUPO","ESTABLECIMIENTO")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/establecimientos/**").hasAnyRole("CLIENTE", "GRUPO","ESTABLECIMIENTO" ) // Cambiado aquí
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/valoracionGrupos/**").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/valoracionEstablecimientos/**").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/clientes/**").hasAnyRole("CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/grupos/**").hasAnyRole("GRUPO")
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                );

        // Añadir el filtro JWT antes del UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

}
