package edu.softwaresecurity.group5.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/*
 * ExternalUserController: customer-home.jsp
 */

public class ExternalUserController {
	@RequestMapping(value="/accountSummary", method=RequestMethod.GET)
	public ModelAndView returnCustomerPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("accountSummary");
		return modelAndView;
	}
}
