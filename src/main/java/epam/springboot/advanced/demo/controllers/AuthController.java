package epam.springboot.advanced.demo.controllers;

import epam.springboot.advanced.demo.utils.JwtUtils;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final Counter counter;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, MeterRegistry registry) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.counter = Counter.builder("login.request.total").
                description("Login Count").
                register(registry);
    }

    public record LoginRequest(String username, String password){}

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        counter.increment();
        var authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()
                        )
                );

        var userDetails = (UserDetails) authentication.getPrincipal();
        var roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Map.of("token", jwtUtils.generateToken(userDetails.getUsername(), roles));
    }
}
