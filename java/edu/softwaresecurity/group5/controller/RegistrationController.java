package edu.softwaresecurity.group5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView registrationForm() {
		ModelAndView model = new ModelAndView();
		model.setViewName("register");		
		return model;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView processRegistration(@RequestParam("usernameInput") String username,
   		 @RequestParam("passwordInput") String password, 
   		 @RequestParam("firstnameInput") String firstname,
   		 @RequestParam("lastnameInput") String lastname,
   		 @RequestParam("phonenumberInput") String phonenumber, 
   		 @RequestParam("emailInput") String email,
   		 @RequestParam("addressInput") String address) {
		 
		 ModelAndView model = new ModelAndView("welcome");
		 model.addObject("username", "Details submitted" + " " + username);
		 return model;
	}
}

