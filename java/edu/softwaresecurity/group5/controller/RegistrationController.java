package edu.softwaresecurity.group5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.softwaresecurity.group5.model.CustomerInformation;

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
	public ModelAndView processRegistration(@ModelAttribute("registerForm") CustomerInformation custInfo) { 
		ModelAndView model = new ModelAndView();
		model.setViewName("welcome");
		return model;
	}
	
}

