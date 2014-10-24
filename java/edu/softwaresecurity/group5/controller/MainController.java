package edu.softwaresecurity.group5.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.service.CustomerService;

@Controller
public class MainController {
	@Autowired
	CustomerService custService;
	
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public ModelAndView indexPage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("index");

		return model;
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		
		ModelAndView model = new ModelAndView();
		model.addObject("title", "You should not be landing here");
		model.addObject("message", "This is default page!");
		model.setViewName("customer-home");
		return model;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("welcomeAdmin");

		return model;
	}
	
	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public ModelAndView employeePage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "You are group5 members!!!!");
		model.addObject("message", "This attributeValuepage is for ROLE_ADMIN only!");
		model.setViewName("employee-home");

		return model;
	}
	
	//for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accessDenied() {

		ModelAndView model = new ModelAndView();
		
		//check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);
		
			model.addObject("username", userDetail.getUsername());	
		}
		model.setViewName("permission-denied");
		return model;
	}
	
	// Displaying the removeUser.jsp page
	@RequestMapping(value="/removeUser", method=RequestMethod.GET)
	public ModelAndView returnRemoveUserPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("removeUser");
		return modelAndView;
	}
	
	// Getting the userdetails
	@RequestMapping(value="/removeUser", method=RequestMethod.POST)
	public ModelAndView getUserDetails(@ModelAttribute("usernameSearch") String usernameSearch) {
		ModelAndView modelAndView = new ModelAndView();
		List<CustomerInformationDTO> customerDetails = new ArrayList<CustomerInformationDTO>();
		customerDetails = custService.fetchUserDetails(usernameSearch);
		modelAndView.addObject("customerDetails", customerDetails);
		modelAndView.setViewName("removeUser");
		return modelAndView;
	}
}