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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import edu.softwaresecurity.group5.dto.BillPayDTO;
import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.model.ModifyUserInformation;
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
		List<BillPayDTO> billPayFromDTO = new ArrayList<BillPayDTO>();

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
			billPayFromDTO = custService.returnBillPaymentDetails(loggedInUser);

			System.out.println(billPayFromDTO);

			// Add it to the model
			modelAndView.addObject("userInformation", custInfoFromDTO);
			modelAndView.addObject("billPayInformation", billPayFromDTO);

			modelAndView.setViewName("accountSummary");
		} else {
			modelAndView.setViewName("permission-denied");
		}
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

	// Debit funds
	// GET Requests
	@RequestMapping(value = "/debitFunds", method = RequestMethod.GET)
	public ModelAndView getDebitFunds() {
		ModelAndView modelAndView = new ModelAndView();
		List<CustomerInformationDTO> custInfoFromDTO = new ArrayList<CustomerInformationDTO>();
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String usernameLoggedIn = userDetail.getUsername();
			custInfoFromDTO = custService.fetchUserDetails(usernameLoggedIn);
			System.out.println(custInfoFromDTO);
			modelAndView.addObject("balanceInformation", custInfoFromDTO);

			modelAndView.setViewName("debitAmount");
		} else {
			modelAndView.setViewName("permission-denied");
		}
		return modelAndView;
	}

	// Debit funds
	// POST requests
	@RequestMapping(value = "/debitFunds", method = RequestMethod.POST)
	public ModelAndView processDebitFunds(
			@RequestParam("debitAmount") String debitAmount) {
		ModelAndView modelAndView = new ModelAndView();
		if (!debitAmount.isEmpty()) {
			Float debitAmountFloat = Float.parseFloat(debitAmount);
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				String usernameLoggedIn = userDetail.getUsername();
				String message = custService.debitAmountForCustomer(
						usernameLoggedIn, debitAmountFloat);
				modelAndView.addObject("debitMessage", message);
				modelAndView.setViewName("debitAmount");
			} else {
				modelAndView.setViewName("permission-denied");
			}
		} else {
			// debitAmount is empty
			modelAndView.addObject("debitMessage",
					"Do not leave the text-box empty!");
			modelAndView.setViewName("debitAmount");
		}
		return modelAndView;
	}

	// Credit funds
	// GET Request
	@RequestMapping(value = "/creditFunds", method = RequestMethod.GET)
	public ModelAndView returnCreditFundsPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("creditAmount");
		return modelAndView;
	}

	// Credit funds
	// POST Request
	@RequestMapping(value = "/creditFunds", method = RequestMethod.POST)
	public ModelAndView processCreditFunds(
			@RequestParam("creditAmount") String creditAmount) {
		ModelAndView modelAndView = new ModelAndView();
		if (!creditAmount.isEmpty()) {
			Float creditAmountFloat = Float.parseFloat(creditAmount);
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				String usernameLoggedIn = userDetail.getUsername();
				String message = custService.creditAmountForCustomer(
						usernameLoggedIn, creditAmountFloat);
				modelAndView.addObject("creditMessage", message);
				modelAndView.setViewName("creditAmount");
			} else {
				modelAndView.setViewName("permission-denied");
			}
		} else {
			modelAndView.addObject("creditMessage",
					"Do not leave the text-box empty!");
			modelAndView.setViewName("creditAmount");
		}
		return modelAndView;
	}

	// GET request for modifyUser
	@RequestMapping(value = "/modifyUserExternal", method = RequestMethod.GET)
	public ModelAndView returnModifyUserPage() {
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
			modelAndView.addObject("userInformation", custInfoFromDTO);
			modelAndView.setViewName("modifyUserExternal");
		} else {
			modelAndView.setViewName("permission-denied");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/modifyUserExternal", method = RequestMethod.POST)
	public ModelAndView processModifyRequest(
			@ModelAttribute("modifyUserRequestAttributes") ModifyUserInformation modInfo) {
		ModelAndView modelAndView = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String requestedForUsername = userDetail.getUsername();
			String requestSubmitMessage = custService.modificationRequest(
					requestedForUsername, modInfo);
			modelAndView
					.addObject("requestSubmitMessage", requestSubmitMessage);

			modelAndView.setViewName("modifyUserExternal");
		} else {
			modelAndView.setViewName("permission-denied");
		}
		return modelAndView;
	}

	// GET request for modifyUser
	@RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
	public ModelAndView returnDeleteAccountPage() {
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
			modelAndView.addObject("userInformation", custInfoFromDTO);
			modelAndView.setViewName("deleteAccount");
		} else {
			modelAndView.setViewName("permission-denied");
		}
		return modelAndView;
	}
	
	// TODO: Doesn't work with empty string, even with .isEmpty() 
	@RequestMapping(value = "/deleteAccount", method = RequestMethod.POST)
	public ModelAndView processModifyRequest(
			@RequestParam("deleteAccount") String deleteAccountYesOrNo) {
		ModelAndView modelAndView = new ModelAndView();
		if (!deleteAccountYesOrNo.isEmpty()) {
			boolean deleteAccount;

			// check if user is login
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				String requestedForUsername = userDetail.getUsername();
				if (deleteAccountYesOrNo.equalsIgnoreCase("Yes")) {
					deleteAccount = true;
					String requestSubmittedMessage = custService.deleteAccount(
							requestedForUsername, deleteAccount);
					modelAndView.setViewName("deleteAccount");
					modelAndView.addObject("requestSubmittedMessage",
							requestSubmittedMessage);
				} else {
					deleteAccount = false;
					String requestSubmittedMessage = custService.deleteAccount(
							requestedForUsername, deleteAccount);
					modelAndView.setViewName("deleteAccount");
					modelAndView.addObject("requestSubmittedMessage",
							requestSubmittedMessage);
				}
			} else {
				modelAndView.setViewName("permission-denied");
			}
		} else {
			System.out.println("here");
			modelAndView.addObject("requestSubmittedMessage",
					"Do not leave the radio button blank");
			modelAndView.setViewName("deleteAccount");
		}
		return modelAndView;
	}
}
