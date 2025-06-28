package uth.edu.dieutrihiemmuon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.SessionManagementFilter;

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
    @Order(1)
    public SecurityFilterChain  adminSecurity(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/auth/login").permitAll()
                        .requestMatchers("/admin/css/**", "/admin/js/**", "/admin/images/**", "/admin/vendors/**").permitAll()
                        .requestMatchers("/admin/employee/index",
                                "/admin/employee/create",
                                "/admin/employee/edit",
                                "/admin/employee/delete",
                                "/admin/servicepackage/**").hasRole("ADMIN")
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
    @Bean
    @Order(2)
    public SecurityFilterChain customSecurity(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/login","/contact","/blog","/about","/services","/register").permitAll()
                        .requestMatchers("/customer/css/**","/customer/js/**","/customer/img/**","/customer/lib/**").permitAll()
                        .requestMatchers("/treatmentcyclecustomer"
                                        ,"/treatmentschedulecustomer"
                                        ,"/appointment/**").hasRole("CUSTOMER")
                        .requestMatchers("/workscheduledoctor"
                                        ,"/treatmentcycledoctor").hasRole("DOCTOR")
                        .requestMatchers("/profile").hasAnyRole("CUSTOMER", "DOCTOR")
                )
                .formLogin(form -> form
                            .loginPage("/login")
                            .defaultSuccessUrl("/",true)
                            .permitAll()
                )
                .logout( logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .exceptionHandling( ex -> ex
                        .accessDeniedPage("/auth/error403")
                );
        return http.build();
    }
}
