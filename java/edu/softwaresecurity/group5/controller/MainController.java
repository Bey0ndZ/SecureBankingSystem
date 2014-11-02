package edu.softwaresecurity.group5.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.model.AddUserInformation;
import edu.softwaresecurity.group5.model.ChangePassword;
import edu.softwaresecurity.group5.service.CustomerService;

@Controller
public class MainController {
	@Autowired
	CustomerService custService;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public ModelAndView indexPage(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {

			Collection<? extends GrantedAuthority> authorities = auth
					.getAuthorities();
			for (GrantedAuthority grantedAuthority : authorities) {
				if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
					return new ModelAndView("forward:/accountSummary");
				} else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
					return new ModelAndView("forward:/admin");
				} else if (grantedAuthority.getAuthority().equals(
						"ROLE_EMPLOYEE")) {
					return new ModelAndView("forward:/employee");

				}
			}

			return new ModelAndView("forward:/welcome");
		}

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error",
					getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("index");

		return model;
	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession()
				.getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else if (exception instanceof SessionAuthenticationException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
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
		model.setViewName("welcomeInternal");

		return model;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accessDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());
		}
		model.setViewName("permission-denied");
		return model;
	}

	// Displaying the removeUser.jsp page
	@RequestMapping(value = "/removeUser", method = RequestMethod.GET)
	public ModelAndView returnRemoveUserPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("removeUser");
		return modelAndView;
	}

	// Getting the userdetails
	@RequestMapping(value = "/removeUser", method = RequestMethod.POST)
	public ModelAndView getUserDetails(
			@RequestParam("removeUser") String usernameSearch) {
		ModelAndView modelAndView = new ModelAndView();
		List<CustomerInformationDTO> customerDetails = new ArrayList<CustomerInformationDTO>();
		customerDetails = custService.fetchUserDetails(usernameSearch);
		modelAndView.addObject("customerDetails", customerDetails);
		modelAndView.setViewName("removeUser");
		return modelAndView;
	}

	// Displaying the ViewUser(SearchUser).jsp page
	@RequestMapping(value = "/viewUser", method = RequestMethod.GET)
	public ModelAndView returnViewUserPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("viewUser");
		return modelAndView;
	}

	// Getting the userdetails
	@RequestMapping(value = "/viewUser", method = RequestMethod.POST)
	public ModelAndView getUserDetail(
			@RequestParam("viewUser") String accountNumber) {
		ModelAndView modelAndView = new ModelAndView();
		CustomerInformationDTO customerDetails = new CustomerInformationDTO();
		customerDetails = custService.getUserFromAccount(accountNumber);
		modelAndView.addObject("customerDetails", customerDetails);
		modelAndView.setViewName("viewUser");
		return modelAndView;
	}

	@RequestMapping("/getList")
	public ModelAndView getUserLIst() {
		List<CustomerInformationDTO> userList = custService.getUserList();
		return new ModelAndView("userList", "userList", userList);
	}

	// Displaying the modify user(modifyUser).jsp page
	@RequestMapping(value = "/modifyUser", method = RequestMethod.GET)
	public ModelAndView returnModifyUserPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("modifyUser");
		return modelAndView;
	}

	// Getting the userdetails
	@RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
	public ModelAndView getmodifyUserDetail(
			@RequestParam("modifyUser") String accountNumber) {
		ModelAndView modelAndView = new ModelAndView();
		CustomerInformationDTO customerDetails = new CustomerInformationDTO();
		customerDetails = custService.getUserFromAccount(accountNumber);
		modelAndView.addObject("customerDetails", customerDetails);
		modelAndView.setViewName("modifyUser");
		return modelAndView;
	}

	// Getting the userdetails
	@RequestMapping(value = "/modifyUserDataBase", method = RequestMethod.POST)
	public ModelAndView getmodifyUserDatabase(
			@ModelAttribute("customerDetails") CustomerInformationDTO customerDetail) {
		ModelAndView modelAndView = new ModelAndView();
		CustomerInformationDTO customerDetails = customerDetail;
		String status = custService.updateAccount(customerDetails);
		modelAndView.addObject("customerDetails", customerDetails);
		modelAndView.addObject("status", status);
		modelAndView.setViewName("modifyUser");
		return modelAndView;
	}

	// change password.
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView returnchangePasswordPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("changePassword");
		return modelAndView;
	}

	// Getting the userdetails
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ModelAndView getchangePassword(
			@ModelAttribute("customerDetails") ChangePassword customerDetail) {
		ModelAndView modelAndView = new ModelAndView();
		ChangePassword customerDetails = customerDetail;
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			customerDetail.setUsername(userDetail.getUsername());
		}
		String status = custService.changeAccountPassword(customerDetails);
		modelAndView.addObject("status", status);
		modelAndView.setViewName("changePassword");
		return modelAndView;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView returnAddUserPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addUser");
		return modelAndView;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ModelAndView returnAddUserPage(
			@Valid @ModelAttribute("addUserForm") AddUserInformation addUserInfo,
			BindingResult result) throws NoSuchAlgorithmException {

		ModelAndView modelAndView = new ModelAndView();
		AddUserValidation.validateForm(addUserInfo, result);

		if (result.hasErrors()) {
			modelAndView.setViewName("addUser");
			return modelAndView;
		} else {
			System.out.println(addUserInfo.getUserName());

			
			custService.insertAddUserInformation(addUserInfo);
			modelAndView.addObject("submitMessage", "User Added Successfully!");
			modelAndView.setViewName("addUser");
			return modelAndView;
		}
	}
	
	@RequestMapping(value = "/unlockAccount", method = RequestMethod.GET)
	public ModelAndView returnUnlockAccountPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("unlockAccount");
		return modelAndView;
	}
	
	// Getting the userdetails
		@RequestMapping(value = "/unlockAccount", method = RequestMethod.POST)
		public ModelAndView getVerfyUserForUnlock(
				@ModelAttribute("customerDetails") CustomerInformationDTO customerDetail) {
			ModelAndView modelAndView = new ModelAndView();
			CustomerInformationDTO customerDetails = customerDetail;
			String status = custService.unlockAccount(customerDetails);
			modelAndView.addObject("status", status);
			modelAndView.setViewName("unlockAccount");
			return modelAndView;
		}
}