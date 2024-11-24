package kz.bitlab.portal.keycloak.service;

import kz.bitlab.portal.keycloak.dto.UserCreateDto;
import kz.bitlab.portal.keycloak.dto.UserSignInDto;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {

    UserRepresentation createUser(UserCreateDto user);
    String signIn(UserSignInDto userSignInDto);
    void changePassword(String userName, String newPassword);
}
