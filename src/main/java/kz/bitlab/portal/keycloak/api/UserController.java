package kz.bitlab.portal.keycloak.api;

import kz.bitlab.portal.keycloak.dto.UserChangePasswordDto;
import kz.bitlab.portal.keycloak.dto.UserCreateDto;
import kz.bitlab.portal.keycloak.dto.UserSignInDto;
import kz.bitlab.portal.keycloak.service.KeycloakService;
import kz.bitlab.portal.keycloak.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public UserRepresentation createUser(@RequestBody UserCreateDto userCreateDto) {
        return keycloakService.createUser(userCreateDto);
    }

    @PostMapping(value = "/sign-in")
    public String signIn(@RequestBody UserSignInDto userSignInDto) {
        return keycloakService.signIn(userSignInDto);
    }

    @PostMapping(value = "/change-password")
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordDto userChangePasswordDto) {

        String currentUserName = UserUtils.getCurrentUserName();
        if (currentUserName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Couldn't Identify User");
        }

        try {
            keycloakService.changePassword(currentUserName, userChangePasswordDto.getNewPassword());
            return ResponseEntity.ok("Password changed!");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error on changing password");
        }
    }
}
