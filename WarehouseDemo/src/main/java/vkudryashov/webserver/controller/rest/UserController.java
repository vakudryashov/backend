package vkudryashov.webserver.controller.rest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import vkudryashov.webserver.model.Role;
import vkudryashov.webserver.model.User;
import vkudryashov.webserver.service.RoleService;
import vkudryashov.webserver.service.SecurityService;
import vkudryashov.webserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    SecurityService securityService;

     @GetMapping("/rest/users/all")
    public List<User> restUsersGetAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/rest/user/{username}")
    public User restUsersGetUser(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @RequestMapping(value = "/rest/users", method = RequestMethod.POST)
    public User restUsersAddUser(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String confirmPassword){
        User user = userService.findByUsername(username);
        if (user != null) throw new ResponseStatusException(HttpStatus.CONFLICT,"Such username is already exists");
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setConfirmPassword(confirmPassword);
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName("ROLE_USER"));
        user.setRoles(roles);
        userService.save(user);
        return user;
    }

    @RequestMapping(value = "/rest/users", method = RequestMethod.PUT)
    public User restUsersEditUser(@RequestParam(value="username") String username,
                                  @RequestParam(value="password", required = false) String password,
                                  @RequestParam(value="confirmPassword", required = false) String confirmPassword,
                                  @RequestParam(value="roles", required = false) String[] roles){
        User user = userService.findByUsername(username);
        if (!securityService.findLoggedInUser().getName().equals(user.getUsername())
                && !securityService.findLoggedInUser().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You do not have permission to change another account");
        }
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Username not found");
        user.setUsername(username);
        user.setPassword(password);
        user.setConfirmPassword(confirmPassword);
        Set<Role> userRoles = new HashSet<>();
        if (roles != null) {
            for (String role : roles) {
                userRoles.add(roleService.findByName(role));
            }
            user.setRoles(userRoles);
        }else userRoles = user.getRoles();
        userService.save(user);
        return user;
    }

    @RequestMapping(value = "/rest/users", method = RequestMethod.DELETE)
    public String restUsersDeleteUser(@RequestParam String username){
        User user = userService.findByUsername(username);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Username not found");
        userService.delete(user);
        if (userService.findByUsername(username) == null) return String.format("User %s Successfully deleted",username);
        else throw new ResponseStatusException(HttpStatus.NOT_MODIFIED,String.format("User %s has not been deleted",user));
    }
}
