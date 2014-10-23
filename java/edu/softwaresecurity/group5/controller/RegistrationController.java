package edu.softwaresecurity.group5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.softwaresecurity.group5.dto.CustomerService;
import edu.softwaresecurity.group5.model.CustomerInformation;

@Controller
@RequestMapping(value="/register")
public class RegistrationController {
	@Autowired
	CustomerService custService;	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView registrationForm() {
		CustomerInformation custInfo = new CustomerInformation();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("customer", custInfo);
		modelAndView.setViewName("register");		
		return modelAndView;
	}	
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView registerCustomer(@ModelAttribute("registerForm") CustomerInformation custInfo) {
		ModelAndView modelAndView = new ModelAndView();
	
		// Have validation annotations/ implement logic
		custService.insertCustomerInformation(custInfo);
		modelAndView.setViewName("welcome");
		modelAndView.addObject("username", custInfo.getUsername());
		return modelAndView;
		
	}
}

