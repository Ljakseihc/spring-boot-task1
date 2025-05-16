package epam.springboot.advanced.demo.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String ADMIN_ROLE = "ADMIN";
    public static final String USER_ROLE = "USER";
    public static final String CUSTOMER_PATH = "/customer/**";
    public static final String PRODUCT_PATH = "/product/**";
    public static final String LOGIN_PATH = "/login";
    public static final String ACTUATOR_PATH = "/actuator/**";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(LOGIN_PATH).permitAll()
                        .requestMatchers(ACTUATOR_PATH).permitAll()

                        .requestMatchers(HttpMethod.GET, CUSTOMER_PATH).hasAnyRole(USER_ROLE, ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST, CUSTOMER_PATH).hasAnyRole(USER_ROLE, ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, CUSTOMER_PATH).hasAnyRole(USER_ROLE, ADMIN_ROLE)
                        .requestMatchers(HttpMethod.GET, PRODUCT_PATH).hasAnyRole(USER_ROLE, ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST, PRODUCT_PATH).hasAnyRole(USER_ROLE, ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, PRODUCT_PATH).hasAnyRole(USER_ROLE, ADMIN_ROLE)

                        .requestMatchers(HttpMethod.DELETE, CUSTOMER_PATH).hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, PRODUCT_PATH).hasRole(ADMIN_ROLE)
//                        .requestMatchers(HttpMethod.GET, ACTUATOR_PATH).hasRole(ADMIN_ROLE)

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}