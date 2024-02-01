package nl.backend.eindopdracht.config;

import nl.backend.eindopdracht.repositories.UserRepository;
import nl.backend.eindopdracht.security.JwtRequestFilter;
import nl.backend.eindopdracht.security.JwtService;
import nl.backend.eindopdracht.security.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService service, UserRepository userRepos) {
        this.jwtService = service;
        this.userRepository = userRepos;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService udService, PasswordEncoder passwordEncoder) {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(udService);
        return new ProviderManager(auth);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

                        //........................USER............................
                        .requestMatchers(HttpMethod.GET, "/user/users").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/user/{id}/{roleName}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/user/{id}/role").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/user/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/user/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/user/{userName}/**").authenticated()
                        //........................ROLE............................
                        .requestMatchers(HttpMethod.GET, "/role/roles").hasRole("MANAGER")
                        //........................Manager............................
                        .requestMatchers("/manageraccount/**").hasRole("MANAGER")
                        //........................Employee............................
                        .requestMatchers("/employeeaccount/**").hasAnyRole("MANAGER", "EMPLOYEE")
                        //........................Customer............................
                        .requestMatchers("/customeraccount/**").hasAnyRole("MANAGER", "CUSTOMER")
                        //........................WORKSCHEDULE............................
                        .requestMatchers(HttpMethod.GET, "/workschedule/{id}").hasAnyRole("MANAGER", "EMPLOYEE","CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/workschedule").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/workschedule/workschedules").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/workschedule/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/workschedule/{id}").hasRole("MANAGER")
                        //........................ORDER............................
                        .requestMatchers(HttpMethod.GET, "/order/{id}").hasAnyRole("MANAGER", "EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/order/orders").hasAnyRole("MANAGER", "EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/order/{id}").hasAnyRole("MANAGER", "EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/order/{id}/invoices").hasAnyRole("MANAGER", "EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/order/{id}/customers").hasAnyRole("MANAGER", "CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/order").hasAnyRole("MANAGER", "CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/order/{id}/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/order/{id}/managers").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/order/{id}").hasRole("MANAGER")
                        //........................Invoice............................
                        .requestMatchers(HttpMethod.GET, "/invoice/{id}").hasAnyRole("MANAGER", "CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/invoice/customer/{customerId}").hasAnyRole("MANAGER", "CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/invoice").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/invoice/invoices").hasAnyRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/invoice/{id}/customer").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/invoice/{id}/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/invoice/{id}").hasRole("MANAGER")
                        //........................Files............................
                        .requestMatchers(HttpMethod.PUT, "/files/**").hasAnyRole("MANAGER", "EMPLOYEE", "CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/files/**").hasAnyRole("MANAGER", "EMPLOYEE", "CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/files/**").hasAnyRole("MANAGER", "EMPLOYEE", "CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE, "/files/**").hasAnyRole("MANAGER", "EMPLOYEE", "CUSTOMER")
                        //........................AUTH............................
                        .requestMatchers(HttpMethod.POST, "/sign_in").permitAll()
                        .requestMatchers(HttpMethod.GET, "/authenticated").authenticated()
                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
