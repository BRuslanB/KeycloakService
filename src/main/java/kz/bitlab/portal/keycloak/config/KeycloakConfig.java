package kz.bitlab.portal.keycloak.config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class KeycloakConfig {

    @Value("${keycloak.url}")
    public String url;

    @Value("${keycloak.realm}")
    public String realm;

    @Value("${keycloak.client}")
    public String client;

    @Value("${keycloak.client-secret}")
    public String clientSecret;

    @Value("${keycloak.username}")
    public String username;

    @Value("${keycloak.password}")
    public String password;

    @Value("${keycloak.grant-type}")
    public String grantType;

    @Bean
    public Keycloak keycloak(){
        log.info("Creating Keycloak Bean");
        return KeycloakBuilder.builder()
                .serverUrl(url)
                .realm(realm)
                .clientId(client)
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .grantType(grantType)
                .build();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
