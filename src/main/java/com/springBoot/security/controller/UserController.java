package com.springBoot.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.springBoot.security.model.User;
import com.springBoot.security.service.SecurityService;
import com.springBoot.security.service.UserService;
import com.springBoot.security.validator.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private SecurityService securityService;
	
	@Autowired
    private UserValidator userValidator;
	
	@GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "index";
    }
	
	@GetMapping("/registration")
    public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
        return "registration";
    }	
	
	@PostMapping("/registration")
	public String createUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {		
		userValidator.validate(user, bindingResult);
		

        if (bindingResult.hasErrors()) {
            return "registration";
        }
		
		//User existingUser =  userService.findByEmail(user.getEmail());
		
		/*
		 * if (existingUser != null) { bindingResult.rejectValue("email", null,
		 * "There is already an account registered with that email"); }
		 */

		
        userService.save(user);
        
        securityService.autoLogin(user.getEmail(), user.getPassword());
		
		return "redirect:/user";
	}
	
}
