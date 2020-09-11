package com.geekbrains.july.warehouse.validator;

import com.geekbrains.july.warehouse.entities.User;
import com.geekbrains.july.warehouse.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserValidator implements Validator {
    private Set<String> skip = new HashSet<>();

    @Autowired
    private UsersService usersService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (!skip.contains("username")) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
            if (user.getLogin().length() < 4 || user.getLogin().length() > 32) {
                errors.rejectValue("username", "Size.userForm.username");
            }
            if (usersService.findByLogin(user.getLogin()).isPresent()) {
                errors.rejectValue("username", "Dublicate.userForm.username");
            }
        }
        if (!skip.contains("password")) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
            if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
                errors.rejectValue("password", "Size.userForm.password");
            }
            if (!user.getConfirmPassword().equals(user.getPassword())) {
                errors.rejectValue("confirmPassword", "Different.userForm.password");
            }
        }
        if (user.getRoleNames() == null){
            user.setRoles(new HashSet<>());
            errors.rejectValue("roleNames", "SelectNone.userForm.roleNames");
        }
        skip.clear();
    }

    public void setSkip(Set<String> skip) {
        this.skip = skip;
    }
}
