package edu.softwaresecurity.group5.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.service.CustomerService;

@Controller
public class TransactionsController {
	@Autowired
	CustomerService custService;

	@RequestMapping(value = "/processBillPayment", method = RequestMethod.GET)
	public ModelAndView returnBillPayPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("billPay");
		return modelAndView;
	}

	@RequestMapping(value = "/processBillPayment", method = RequestMethod.POST)
	public ModelAndView processBillPayment(
			@RequestParam("accountNumberOfCustomer") String accountNumber,
			@RequestParam("amountToBeTransferred") String amountToBeTransferred) {
		ModelAndView modelAndView = new ModelAndView();

		if (!(accountNumber.isEmpty() || amountToBeTransferred.isEmpty())) {

			Document inputAccountNumber = Jsoup.parse(accountNumber);
			Document inputAmountToBeTransferred = Jsoup
					.parse(amountToBeTransferred);

			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				String loggedInUser = userDetail.getUsername();
				modelAndView.addObject("username", loggedInUser);

				if (custService.processBillPay(loggedInUser,
						inputAccountNumber.text(),
						inputAmountToBeTransferred.text())) {
					modelAndView.addObject("submitMessage",
							"Request submitted.");
				} else {
					modelAndView
							.addObject("submitMessage",
									"Request cannot be proccessed. Contact employee or admin.");
				}

			} else {
				modelAndView.setViewName("permission-denied");
			}
		} else {
			modelAndView.addObject("submitMessage",
					"Do not leave either of the text-boxes empty");
			modelAndView.setViewName("billPay");
		}
		modelAndView.setViewName("billPay");
		return modelAndView;
	}

	@RequestMapping(value = "/transactionReview", method = RequestMethod.GET)
	public ModelAndView returnTransactionsReviewPage() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String loggedInUser = userDetail.getUsername();

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
			@RequestParam("accountNumber") String accountnumber) {
		ModelAndView modelAndView = new ModelAndView();
		CustomerInformationDTO customerDetails = new CustomerInformationDTO();
		customerDetails = custService.getUserFromAccount(accountnumber);
		modelAndView.addObject("customerDetails", customerDetails);
		modelAndView.setViewName("transferMoney");
		return modelAndView;
	}

	// Getting the transfer/pending
	@RequestMapping(value = "/transferMoneyConfirmation", method = RequestMethod.POST)
	public ModelAndView updateAccount(
			@RequestParam("accountNo") String accountnumber,
			@RequestParam("amount") String transfer, @RequestParam String action) {
		ModelAndView modelAndView = new ModelAndView();
		if (action.equals("Transfer")) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				String loggedInUser = userDetail.getUsername();
				modelAndView.addObject("username", loggedInUser);

				if (custService.transfer(loggedInUser, accountnumber, transfer)) {
					modelAndView.addObject("submitMessage",
							"Transfer Processed.");
				} else {
					modelAndView
							.addObject("submitMessage",
									"Request cannot be proccessed. Contact employee or admin.");
				}
			}
			modelAndView.setViewName("transferMoney");

		} else if (action.equals("Approve Transfer")) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				String loggedInUser = userDetail.getUsername();
				modelAndView.addObject("username", loggedInUser);

				if (custService.pendingTransfer(loggedInUser, accountnumber,
						transfer)) {
					modelAndView.addObject("submitMessage",
							"Request submitted.");
				} else {
					modelAndView
							.addObject("submitMessage",
									"Request cannot be proccessed. Contact employee or admin.");
				}
			}
			modelAndView.setViewName("transferMoney");

		}
		return modelAndView;
	}
}