package com.geekbrains.july.warehouse.controllers.web;

import com.geekbrains.july.warehouse.entities.User;
import com.geekbrains.july.warehouse.services.RoleService;
import com.geekbrains.july.warehouse.services.UsersService;
import com.geekbrains.july.warehouse.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;


@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;


    @Autowired
    private RoleService roleService;

    @Autowired
    UserValidator userValidator;

    @GetMapping(value = {"/registration"})
    public String userAdd(@ModelAttribute("userForm") User userForm,
                          BindingResult bindingResult,
                          Model model){

        model.addAttribute("userName",usersService.findLoggedInUser().get().getFullname());
        model.addAttribute("roles",roleService.findAll());
        return "users/add";
    }

    @PostMapping(value = "/users/add")
    public String registration(@ModelAttribute("userForm") User userForm,
                               BindingResult bindingResult,
                               Model model){
        User user = usersService.findLoggedInUser().get();
        model.addAttribute("userName",user.getFullname());
        model.addAttribute("roles",roleService.findAll());
        userValidator.validate(userForm, bindingResult);
        if(bindingResult.hasErrors()){
            return "/users/add";
        }
        usersService.setRoles(userForm);
        usersService.save(userForm);
        model.addAttribute("userForm",new User());
        return "redirect:/users";
    }

    @GetMapping(value = {"/users"})
    public String userFind(Model model){
        User user = usersService.findLoggedInUser().get();
        model.addAttribute("userName",user.getFullname());
        model.addAttribute("users", usersService.findAll());
        return "/users/find";
    }

    @GetMapping(value = {"/users/edit"})
    public String userEdit(@RequestParam(value="username") String username,
                           @ModelAttribute("userForm") User userForm,
                           BindingResult bindingResult,
                           Model model){
        User user = usersService.findByLogin(username).get();
        User loggedInUser = usersService.findLoggedInUser().get();
        model.addAttribute("userName",loggedInUser.getFullname());
        model.addAttribute("userForm", user);
        model.addAttribute("roles",roleService.findAll());
        return "/users/edit";
    }

    @PostMapping(value = "/users/edit")
    public String userEdit(@ModelAttribute("userForm") User userForm,
                           BindingResult bindingResult,
                           Model model){
        User user = usersService.findLoggedInUser().get();
        model.addAttribute("userName",user.getFullname());
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
            usersService.setRoles(userForm);
            return "/users/edit";
        }
        usersService.setRoles(userForm);
        userForm.setId(user.getId());
        usersService.save(userForm);
        return "redirect:/users";
    }

    @GetMapping(value = {"/users/delete"})
    public String userDelete(@RequestParam(value="username") String username,
                             Model model){
        if (username.equals("admin")){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Этого пользователя удалять запрещено");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"У вас нет полномочий удалять профили пользователей");
        }
        User user = usersService.findByLogin(username).get();
        if(!usersService.findByLogin(username).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь не найден");
        }
        usersService.delete(user);
        return "redirect:/users";
    }


}
