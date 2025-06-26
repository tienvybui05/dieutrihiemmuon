package uth.edu.dieutrihiemmuon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/auth/login","/admin/customer/index","/admin/customer/create").permitAll()
                        .requestMatchers("/admin/css/**", "/admin/js/**", "/admin/images/**", "/admin/vendors/**").permitAll()
                        .requestMatchers("/admin/employee/index",
                                "/admin/employee/create",
                                "/admin/employee/edit",
                                "/admin/employee/delete").hasRole("ADMIN")
                        .anyRequest().hasAnyRole("ADMIN","EMPLOYEE")
                )
                .formLogin(form -> form
                        .loginPage("/admin/auth/login")
                        .defaultSuccessUrl("/admin",true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/auth/logout")
                        .logoutSuccessUrl("/admin/auth/login?logout")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/admin/auth/error403")
                );
        return http.build();
    }
}
