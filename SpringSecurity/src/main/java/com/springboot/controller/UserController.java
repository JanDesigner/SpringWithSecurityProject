package com.springboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.model.User;
import com.springboot.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = { "/" , "/login"} )
	public ModelAndView login() {
		ModelAndView model = new ModelAndView();
		model.setViewName("user/login");
		return model;
	}
	@RequestMapping(value= { "/signup" } , method=RequestMethod.GET )
	public ModelAndView signup() {
		ModelAndView model = new ModelAndView();
		User user = new User();
		model.addObject("user" , user );
		model.setViewName("user/signup");
		return model;
	}
	
	@RequestMapping(value = { "/signup" } , method=RequestMethod.POST)
	public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		User userExist = userService.findUserByEmail(user.getEmail());
		
		if(userExist != null) {
			bindingResult.rejectValue("email", "error.user" , "This email already exist");
		}
		if(bindingResult.hasErrors()) {
			model.setViewName("user/signup");
		}else {
			userService.saveUser(user);
			model.addObject("msg" , "User has been registered successfully");
			model.addObject("user" , new User());
			model.setViewName("user/signup");
		}
		
		return model;
	}
	@RequestMapping(value = { "/home/home" }  )
	public ModelAndView home() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		model.addObject("username" , user.getFirstName() + " " + user.getLastName());
		model.setViewName("home/home");
		return model;
	}
	
	@RequestMapping(value = { "/access_denied"} )
	public ModelAndView accessDenied() {
		ModelAndView model = new ModelAndView();
		model.setViewName("errors/access_denied");
		return model;
	}
	
}
