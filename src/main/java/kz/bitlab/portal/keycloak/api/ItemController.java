package kz.bitlab.portal.keycloak.api;

import kz.bitlab.portal.keycloak.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ItemController {

    @GetMapping(value = "/home")
    @PreAuthorize("isAuthenticated")
    public String homePage(){
        return "This id home page " + UserUtils.getCurrentUserName();
    }

    @GetMapping(value = "/about")
    @PreAuthorize("hasRole('USER')")
    public String aboutPage(){
        return "This id about page " + UserUtils.getCurrentUserFirstName() + " - " + UserUtils.getCurrentUserLastName();
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage(){
        return "This id admin page " + UserUtils.getCurrentUserEmail();
    }
}
