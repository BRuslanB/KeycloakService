package kz.bitlab.portal.keycloak.service;

import jakarta.ws.rs.core.Response;
import kz.bitlab.portal.keycloak.dto.UserCreateDto;
import kz.bitlab.portal.keycloak.dto.UserSignInDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class KeycloakService {

    private final Keycloak keycloak;
    private final RestTemplate restTemplate;

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

    public UserRepresentation createUser(UserCreateDto user) {

        UserRepresentation newUser = new UserRepresentation();
        newUser.setEmail(user.getEmail());
        newUser.setEmailVerified(true);
        newUser.setUsername(user.getUsername());
        newUser.setFirstName(user.firstName);
        newUser.setLastName(user.getLastName());
        newUser.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(user.getPassword());
        credential.setTemporary(false);

        newUser.setCredentials(List.of(credential));

        Response response = keycloak
                .realm(realm)
                .users()
                .create(newUser);

        if (response.getStatus() != HttpStatus.CREATED.value()) { //201 status
            log.error("Error on creating user");
            throw new RuntimeException("Failed to create user!");
        }

        List<UserRepresentation> searchUsers = keycloak.realm(realm).users().search(user.getUsername());
        return searchUsers.get(0);
    }

    public String signIn(UserSignInDto userSignInDto) {

        String tokenEndpoint = url + "/realms/" + realm + "/protocol/openid-connect/token";

        MultiValueMap <String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", grantType);
        formData.add("client_id", client);
        formData.add("client_secret", clientSecret);
        formData.add("username", userSignInDto.username);
        formData.add("password", userSignInDto.password);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, new HttpEntity<>(formData, headers), Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (!response.getStatusCode().is2xxSuccessful() || responseBody == null) {
            log.error("Error sign in!");
            throw new RuntimeException("Failed to authenticated");
        }

        return (String) responseBody.get("access_token");
    }
}
