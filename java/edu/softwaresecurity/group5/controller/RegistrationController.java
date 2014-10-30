package edu.softwaresecurity.group5.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
	public ModelAndView registerCustomer(@Valid @ModelAttribute("registerForm") CustomerInformation custInfo,
		   BindingResult result, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		RegistrationValidation.validateForm(custInfo, result);
		
		String remoteAddr = request.getRemoteAddr();
		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey("6LelnPwSAAAAAEIVuVPz5_wWsq3skomEaVJ_5eZH");
		String challenge = request.getParameter("recaptcha_challenge_field");
		String uresponse = request.getParameter("recaptcha_response_field");
		ReCaptchaResponse reCaptchaResponse =
		    reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
		
		if (!reCaptchaResponse.isValid()) {
			System.out.println("Entered in captcha error!");
			FieldError fieldError = new FieldError(
			        "comment",
			        "captcha",
			        "Please try again.");
			    result.addError(fieldError);
		}
		
        if (result.hasErrors()) {
        	modelAndView.setViewName("register");
                return modelAndView;
        } else {        	
			System.out.println(custInfo.getUsername());
			custService.insertCustomerInformation(custInfo);
			modelAndView.setViewName("index");
			return modelAndView;
        }
	}
}

