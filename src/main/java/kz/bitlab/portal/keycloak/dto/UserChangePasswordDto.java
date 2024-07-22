package kz.bitlab.portal.keycloak.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserChangePasswordDto {

    public String newPassword;
}
