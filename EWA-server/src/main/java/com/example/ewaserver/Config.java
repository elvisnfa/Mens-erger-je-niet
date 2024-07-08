package com.example.ewaserver;

import com.example.ewaserver.notifications.AnnouncementDistributor;
import com.example.ewaserver.notifications.NotificationDistributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Set;


@Configuration
@EnableWebMvc
@EnableWebSocket
public class Config implements WebMvcConfigurer , WebSocketConfigurer {
    @Autowired
    private AnnouncementDistributor announcementDistributor;

    @Autowired
    private NotificationDistributor notificationDistributor;

    @Value("${allowed.cors.clients:http://*.hva.nl:*}")
    private String allowedCorsClients;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public static final String IP_FORWARDED_FOR = "X-Forwarded-For";
    public static final String REFRESH_TOKEN =  "Refresh_token";

    // path prefixes that will be protected by the authentication filter
    public Set<String> SECURED_PATHS =
            Set.of("/users");

    // a variable reboot signature can be used as an additional security layer in authentication tokens.
    private static final double REBOOT_CODE = 63.0427; // Math.random();

    // JWT configuration that can be adjusted from application.properties
    @Value("${jwt.issuer:EWA_team_2}")
    private String issuer;

    @Value("${jwt.passphrase:This is very secret information for my private encryption key.}")
    private String passphrase;

    @Value("${jwt.passphrase:This is very secret information for my private refresh encryption key.}")
    private String refreshPassphrase;

    @Value("${jwt.duration-of-validity:1200}") // default 20 minutes;
    private int tokenDurationOfValidity;

    @Value("${jwt.duration-of-validity:6000}") // default 100 minutes;
    private int refreshTokenDurationOfValidity;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").
                allowedOriginPatterns("http://localhost:*", "http://*.hva.nl:*", "https://2-ludo-be-app-production.up.railway.app/*",
                        "wss://2-ludo-be-app-production.up.railway.app/*", "*", getHostIPAddressPattern(), allowedCorsClients)
                .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, IP_FORWARDED_FOR, REFRESH_TOKEN)
                .exposedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, IP_FORWARDED_FOR, REFRESH_TOKEN)
                .allowCredentials(true);

    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.announcementDistributor, "/announcements")
                .setAllowedOriginPatterns("http://localhost:*",  "http://*.hva.nl:*", "https://2-ludo-be-app-production.up.railway.app/*",
                        "wss://2-ludo-be-app-production.up.railway.app/*", "*" ,getHostIPAddressPattern(), allowedCorsClients)
        //.withSockJS()
        ;
        registry.addHandler(this.notificationDistributor, "/notifications")
                .setAllowedOriginPatterns("http://localhost:*", "http://*.hva.nl:*", "https://2-ludo-be-app-production.up.railway.app/*",
                        "wss://2-ludo-be-app-production.up.railway.app/*", "*", getHostIPAddressPattern(), allowedCorsClients)
        //.withSockJS()
        ;

    }
    private String getHostIPAddressPattern() {
        try {
            return "http://" + Inet4Address.getLocalHost().getHostAddress() + ":*";
        } catch (UnknownHostException ignored) {
        }
        return "http://192.168.*.*:*";
    }

    public String getIssuer() {
        // include a reboot sequence nr in the issuer signature
        //  such that authentication tokens can be revoked after a reboot.
        return String.format("%s-%f", this.issuer, REBOOT_CODE);
    }

    public String getPassphrase() {
        return passphrase;
    }

    public int getTokenDurationOfValidity() {
        return tokenDurationOfValidity;
    }

    public String getRefreshPassphrase() {
        return refreshPassphrase;
    }

    public int getRefreshTokenDurationOfValidity() {
        return refreshTokenDurationOfValidity;
    }


}
