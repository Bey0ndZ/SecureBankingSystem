package edu.softwaresecurity.group5.controller;

import java.util.ArrayList;
import java.util.List;

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
import edu.softwaresecurity.group5.dto.UserTransactionsDTO;
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
			int length1 = accountNumber.length();
			int length2 = amountToBeTransferred.length();
			int counter = 0;
			for (char ch : accountNumber.toCharArray()) {
				if (Character.isDigit(ch) == false) {
					counter++;
				}
			}
			int counter1 = 0;
			for (char ch : amountToBeTransferred.toCharArray()) {
				if (Character.isDigit(ch) == false) {
					counter1++;
				}
			}
			if (counter > 0 || length1 != 8) {
				modelAndView.addObject("errorMsg",
						"Please enter the correct account number and amount!");
				modelAndView.setViewName("billPay");
				return modelAndView;
			} else if (counter1 > 0) {
				modelAndView.addObject("errorMsg",
						"Please enter the correct account numebr and amount!");
				modelAndView.setViewName("billPay");
				return modelAndView;
			} else {

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
			}
		} else {
			modelAndView.addObject("submitMessage",
					"Do not leave either of the text-boxes empty");
			modelAndView.setViewName("billPay");
		}
		modelAndView.setViewName("billPay");
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
			int counter = 0;
			try
			{
			for (char ch: accountnumber.toCharArray()) {
				if (Character.isDigit(ch)) {
					counter ++;
				}
			}
			
			if (counter != 8 || accountnumber.length() != 8) {
				modelAndView.addObject("errorMsg", "Please enter the correct account number!");
				modelAndView.setViewName("transferMoney");
			}
			else {
				CustomerInformationDTO customerDetails = new CustomerInformationDTO();
				customerDetails = custService.getUserFromAccount(accountnumber);
				modelAndView.addObject("customerDetails", customerDetails);
				modelAndView.setViewName("transferMoney");
			}
		}
			catch(Exception e)
			{
				modelAndView.addObject("errorMsg", " Please enter the correct account no. ");
				modelAndView.setViewName("transferMoney");
			}
			return modelAndView;
		}
		// Getting the transfer/pending
				@RequestMapping(value = "/transferMoneyConfirmation", method = RequestMethod.POST)
				public ModelAndView updateAccount(
						@RequestParam("accountNo") String accountnumber,
						@RequestParam("amount") String transfer, @RequestParam String action) {
					ModelAndView modelAndView = new ModelAndView();
					try{
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
												"Request cannot be proccessed. ");
							}
						}
						modelAndView.setViewName("transferMoney");

					} else if (action.equals("Request Approval")) {
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
												"Request cannot be proccessed.");
							}
						}
						modelAndView.setViewName("transferMoney");
					}
				}
					catch(Exception e)
					{
						modelAndView.addObject("errorMsg", " Please enter a correct amount ");
						modelAndView.setViewName("transferMoney");
					}
					return modelAndView;
				}

	// GET Transactions
	// Transactions Review
	
	List<String> t_id = new ArrayList<String>();
	@RequestMapping(value = "/transactionReview", method = RequestMethod.GET)
	public ModelAndView returnTransactionsReviewPage() {
		ModelAndView modelAndView = new ModelAndView();
		List<UserTransactionsDTO> userTransactions = new ArrayList<UserTransactionsDTO>();

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String loggedInUser = userDetail.getUsername();
			userTransactions = custService.getUserTransactions(loggedInUser);

			// Add the object to model
			
			for (UserTransactionsDTO userTransactionsEach : userTransactions) {
				t_id.add(userTransactionsEach.getUsernameToAccountNumber());
			}
			
			System.out.println("CHECK: "+userTransactions);
			
			modelAndView.addObject("userTransactions", userTransactions);
			modelAndView.setViewName("transactionsReview");
		} else {
			modelAndView.setViewName("permission-denied");
		}
		return modelAndView;
	}

	// Delete Transactions
	// POST from transactionsReview.jsp page
	@RequestMapping(value = "/deleteTransaction", method = RequestMethod.POST)
	public ModelAndView deleteSuccessPage(
			@RequestParam("deleteTransactionID") String deleteTxID) {
		ModelAndView modelAndView = new ModelAndView();
		
		//----
		int count = 0;
		for (char c: deleteTxID.toCharArray()) {
			if(Character.isDigit(c)) {
				count ++;
			}
		}
		
		boolean flag = true;
		if (!t_id.contains(deleteTxID)) {
			flag = false;
		}
		else {
			flag= true;
		}
		
		if (deleteTxID.length() != count || deleteTxID.length() == 0 || deleteTxID.length() != 6 || flag==false) {
			modelAndView.addObject("errorMsg", "Please enter the correct transaction ID!");
			modelAndView.setViewName("transactionsReview");
			return modelAndView;
		}
		
		else if (!deleteTxID.isEmpty()) {
			// Strip HTML to prevent XSS
			Document doc = Jsoup.parse(deleteTxID);
			String txID = doc.text();

			Integer txIDInInt = Integer.parseInt(txID);

			// Call the tx delete method
			if (custService.deleteTx(txIDInInt)) {
				modelAndView.addObject("deleteInformation",
						"Deleted transaction.");
			} else {
				modelAndView
						.addObject("deleteInformation",
								"Deletion unsuccessful. Please contact the admin or employee");
			}

		} else {
			modelAndView.addObject("emptyBox",
					"You cannot leave the text-box empty");
		}
		modelAndView.setViewName("deleteTransactionSuccess");
		return modelAndView;
	}
	
	// After a delete is processed, show the users the page
	@RequestMapping(value="/deleteTransaction", method=RequestMethod.GET)
	public ModelAndView returnDeleteTransactionSuccessPage() {
		// Call the getUserTransactions method
		ModelAndView modelAndView = new ModelAndView();
		List<UserTransactionsDTO> userTransactions = new ArrayList<UserTransactionsDTO>();

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String loggedInUser = userDetail.getUsername();
			userTransactions = custService.getUserTransactions(loggedInUser);

			// Add the object to model
			modelAndView.addObject("userTransactions", userTransactions);
			modelAndView.setViewName("deleteTransactionSuccess");
		} else {
			modelAndView.setViewName("permission-denied");
		}
		return modelAndView;
	}
}