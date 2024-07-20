package kz.bitlab.portal.keycloak.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateDto {

    public String email;
    public String username;
    public String firstName;
    public String lastName;
    public String password;
}
