package vkudryashov.webserver.controller.web;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;
import vkudryashov.webserver.model.User;
import vkudryashov.webserver.service.RoleService;
import vkudryashov.webserver.service.SecurityService;
import vkudryashov.webserver.service.UserService;
import vkudryashov.webserver.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Controller for {@link User}'s pages.
 *
 * @author Vladimir Kudryashov
 * @version 1.0
 * */

@Controller
public class DirectoriesController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    UserValidator userValidator;

    @GetMapping(value = {"/content/directories/roles"})
    public String rolesList(Model model){
        Authentication loggedInUser = securityService.findLoggedInUser();
        User user = userService.findByUsername(loggedInUser.getName());
        model.addAttribute("name",user.getFullname());
        model.addAttribute("roles", roleService.findAll());
        return "/directories/roles";
    }

}
