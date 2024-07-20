package kz.bitlab.portal.keycloak.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserSignInDto {

    public String username;
    public String password;
}
