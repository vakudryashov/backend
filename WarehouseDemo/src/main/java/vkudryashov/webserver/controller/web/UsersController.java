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
public class UsersController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    UserValidator userValidator;

    @GetMapping(value = {"/content/users/add"})
    public String userAdd(@ModelAttribute("userForm") User userForm,
                          BindingResult bindingResult,
                          Model model){
        Authentication loggedInUser = securityService.findLoggedInUser();
        model.addAttribute("name",loggedInUser.getName());
        model.addAttribute("roles",roleService.findAll());
        return "users/add";
    }

    @PostMapping(value = "/content/users/add")
    public String registration(@ModelAttribute("userForm") User userForm,
                               BindingResult bindingResult,
                               Model model){
        Authentication loggedInUser = securityService.findLoggedInUser();
        User user = userService.findByUsername(loggedInUser.getName());
        model.addAttribute("name",user.getFullname());
        model.addAttribute("roles",roleService.findAll());
        userValidator.validate(userForm, bindingResult);
        if(bindingResult.hasErrors()){
            return "/users/add";
        }
        userService.setRoles(userForm);
        userService.save(userForm);
        model.addAttribute("userForm",new User());
        return "redirect:/content/users/find";
    }

    @GetMapping(value = {"/content/control/users"})
    public String userFind(Model model){
        Authentication loggedInUser = securityService.findLoggedInUser();
        User user = userService.findByUsername(loggedInUser.getName());
        model.addAttribute("name",user.getFullname());
        model.addAttribute("users", userService.findAll());
        return "/control/users";
    }

    @GetMapping(value = {"/content/users/edit"})
    public String userEdit(@RequestParam(value="username") String username,
                           @ModelAttribute("userForm") User userForm,
                           BindingResult bindingResult,
                           Model model){
        User user = userService.findByUsername(username);
        Authentication loggedInUser = securityService.findLoggedInUser();
        model.addAttribute("name",loggedInUser.getName());
        model.addAttribute("userForm", user);
        model.addAttribute("roles",roleService.findAll());
        return "/users/edit";
    }

    @PostMapping(value = "/content/users/edit")
    public String userEdit(@ModelAttribute("userForm") User userForm,
                           BindingResult bindingResult,
                           Model model){
        Authentication loggedInUser = securityService.findLoggedInUser();
        User user = userService.findByUsername(userForm.getUsername());
        model.addAttribute("name",loggedInUser.getName());
        model.addAttribute("roles",roleService.findAll());
        Set<String> skip = new HashSet<>();
        skip.add("username");
        if (userForm.getPassword().equals("")){
            skip.add("password");
            userForm.setPassword(user.getPassword());
            userForm.setConfirmPassword(null);
        }
        userValidator.setSkip(skip);
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()){
            return "/users/edit";
        }
        userService.setRoles(userForm);
        userForm.setId(user.getId());
        userService.save(userForm);
        return "redirect:/content/control/users";
    }

    @GetMapping(value = {"/content/users/delete"})
    public String userDelete(@RequestParam(value="username") String username,
                             Model model){
        if (username.equals("Administrator")){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Этого пользователя удалять запрещено");
        }
        Authentication loggedInUser = securityService.findLoggedInUser();
        if (!loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"У вас нет полномочий удалять профили пользователей");
        }
        User user = userService.findByUsername(username);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь не найден");
        }
        userService.delete(user);
        return "redirect:/content/control/users";
    }

}
