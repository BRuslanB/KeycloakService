package kz.bitlab.portal.keycloak.api;

import kz.bitlab.portal.keycloak.dto.UserCreateDto;
import kz.bitlab.portal.keycloak.dto.UserSignInDto;
import kz.bitlab.portal.keycloak.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final KeycloakService keycloakService;

    @PostMapping(value = "/create")
    public UserRepresentation createUser(@RequestBody UserCreateDto userCreateDto) {
        return keycloakService.createUser(userCreateDto);
    }

    @PostMapping(value = "/sign-in")
    public String signIn(@RequestBody UserSignInDto userSignInDto) {
        return keycloakService.signIn(userSignInDto);
    }
}
