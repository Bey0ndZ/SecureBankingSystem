package edu.softwaresecurity.group5.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.service.CustomerService;

/*
 * ExternalUserController: accountSummary.jsp
 */

@Controller
@SessionAttributes("username")
public class ExternalUserController {
	@Autowired
	CustomerService custService;

	@RequestMapping(value = "/accountSummary", method = RequestMethod.GET)
	public ModelAndView returnCustomerPage(HttpServletRequest session) {
		ModelAndView modelAndView = new ModelAndView();
		List<CustomerInformationDTO> custInfoFromDTO = new ArrayList<CustomerInformationDTO>();
		
		// check if user is login
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String loggedInUser = userDetail.getUsername();
			modelAndView.addObject("username", loggedInUser);
			System.out.println(loggedInUser);
			
			// Call the DAOImpl layer
			custInfoFromDTO = custService.fetchUserDetails(loggedInUser);
			
			// Add it to the model
			modelAndView.addObject("userInformation", custInfoFromDTO);
		}
		modelAndView.setViewName("accountSummary");
		return modelAndView;
	}

	// Displaying the transferMoney.jsp page
	@RequestMapping(value = "/transferMoney", method = RequestMethod.GET)
	public ModelAndView returnTranferMoneyPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("transferMoney");
		return modelAndView;
	}

	// Displaying the transferActivity.jsp page
	@RequestMapping(value = "/transferActivity", method = RequestMethod.GET)
	public ModelAndView returnTranferActivityPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("transferActivity");
		return modelAndView;
	}

	// Getting the userdetails
	@RequestMapping(value = "/transferMoney", method = RequestMethod.POST)
	public ModelAndView getUserDetail(
			@RequestParam("verifyUser") String accountNumber) {
		ModelAndView modelAndView = new ModelAndView();
		CustomerInformationDTO customerDetails = new CustomerInformationDTO();
		customerDetails = custService.getUserFromAccount(accountNumber);
		modelAndView.addObject("customerDetails", customerDetails);
		modelAndView.setViewName("transferMoney");
		return modelAndView;
	}
}
