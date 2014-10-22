package edu.softwaresecurity.group5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/register")
public class RegistrationController {
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView registrationForm() {
		ModelAndView model = new ModelAndView();
		model.setViewName("register");		
		return model;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView processRegistration() {
		 
		 ModelAndView model = new ModelAndView();
		 model.setViewName("welcome");
		 model.addObject("username", "Bey0ndZ");
		 return model;
	}
	
}

