package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.SecurityService;
import com.example.demo.service.UserService;
import com.example.demo.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for {@link User}'s pages.
 *
 * @author Vladimir Kudryashov
 * @version 1.0
 * */

@Controller
public class userController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,Model model){
        userValidator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors()){
            return "registration";
        }
        userService.save(userForm);

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login2", method = RequestMethod.GET)
    public String login(Model model, String error, String logout){
        if (error != null ){
            model.addAttribute("error","username or password is incorrect");
        }

        if (logout != null){
            model.addAttribute("message","logged out successfully.");
        }
        if (securityService.findLoggedInUser().isAuthenticated()) System.out.println(securityService.findLoggedInUser().getName());
        return "login2";
    }

    @RequestMapping(value = {"/","/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model){
        model.addAttribute("name",securityService.findLoggedInUser().getName());
        model.addAttribute("role",securityService.findLoggedInUser().getAuthorities());
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)

    public String admin(Model model){
        return "admin";
    }
}
