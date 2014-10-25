package edu.softwaresecurity.group5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/*
 * ExternalUserController: accountSummary.jsp
 */

@Controller
public class ExternalUserController {
	@RequestMapping(value="/accountSummary", method=RequestMethod.GET)
	public ModelAndView returnCustomerPage() {
		System.out.println("Hello world!");
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("accountSummary");
		return modelAndView;
	}
}
