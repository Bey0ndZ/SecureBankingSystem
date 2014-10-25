package edu.softwaresecurity.group5.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.softwaresecurity.group5.model.CustomerInformation;
import edu.softwaresecurity.group5.service.CustomerService;

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
	public ModelAndView registerCustomer(@Valid @ModelAttribute("registerForm") CustomerInformation custInfo, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();
		
		RegistrationValidation.validate (custInfo, result);
		System.out.println("COUNT "+result.getErrorCount());
		
		System.out.println("ERROR "+result.getAllErrors());
		
        if (result.hasErrors()) {
        	System.out.println("CHECKING 3");
                return modelAndView;
                
        }
		System.out.println(custInfo.getUsername());
        // Have validation annotations/ implement logic
		custService.insertCustomerInformation(custInfo);
		modelAndView.setViewName("login");
		//modelAndView.addObject("username", custInfo.getUsername());
		System.out.println("CHECKING 4");
		return modelAndView;
			
	}
}

