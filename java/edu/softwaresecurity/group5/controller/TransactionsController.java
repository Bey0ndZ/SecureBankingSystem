package edu.softwaresecurity.group5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.softwaresecurity.group5.service.CustomerService;

@Controller
public class TransactionsController {
	@Autowired
	CustomerService custService;
	
	@RequestMapping(value="/processBillPayment", method=RequestMethod.GET)
	public ModelAndView returnBillPayPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("billPay");
		return modelAndView;
	}
	
	@RequestMapping(value="/processBillPayment", method=RequestMethod.POST)
	public ModelAndView processBillPayment(@RequestParam("accountNumberOfCustomer")
	String accountNumber, @RequestParam("amountToBeTransferred") String amountToBeTransferred) {
		ModelAndView modelAndView = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String loggedInUser = userDetail.getUsername();
			modelAndView.addObject("username", loggedInUser);
			
		if (custService.processBillPay(loggedInUser, accountNumber, amountToBeTransferred)) {
			modelAndView.addObject("submitMessage", "Request submitted.");
		} else {
			modelAndView.addObject("submitMessage", "Request cannot be proccessed. Contact employee or admin.");
		}
		
		
	}
	modelAndView.setViewName("billPay");
	return modelAndView;
}
}