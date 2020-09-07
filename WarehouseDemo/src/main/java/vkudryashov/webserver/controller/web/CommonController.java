package vkudryashov.webserver.controller.web;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

/**
 * Controller for {@link User}'s pages.
 *
 * @author Vladimir Kudryashov
 * @version 1.0
 * */

@Controller
public class CommonController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    UserValidator userValidator;

    @GetMapping(value = {"/"})
    public String startPage(){
        System.out.println("/");
        Authentication loggedInUser = securityService.findLoggedInUser();
        if (loggedInUser == null ) return "redirect:/login";
        if (loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/content/home/admin";
        }else{
            return "redirect:/content/home/user";
        }
    }

    @GetMapping(value = {"/login"})
    public String login(){
        return "/login";
    }

    @GetMapping(value = "/logout")
    public String logout(Model model, String error, String logout){
        securityService.findLoggedInUser().setAuthenticated(false);
        return "redirect:/";
    }

    @GetMapping(value = {"/content/{group}/{page}"})
    public String page(@PathVariable String group,
                       @PathVariable String page,
                       Model model){
        Authentication loggedInUser = securityService.findLoggedInUser();
        User user = userService.findByUsername(loggedInUser.getName());
        model.addAttribute("name",user.getFullname());
        return String.format("%s/%s",group,page);
    }
}
