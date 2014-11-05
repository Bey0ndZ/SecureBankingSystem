package edu.softwaresecurity.group5.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
			int counter = 0;
			for (char ch: debitAmount.toCharArray()) {
				if (Character.isDigit(ch) == false) {
					counter ++;
				}
			}
			if (counter>0) {
				modelAndView.addObject("errorMsg", "Please enter the correct amount!");
				modelAndView.setViewName("debitAmount");
				return modelAndView;
			}
			else {
				// Removing the html tags (if present) using JSoup library
				Document inputText = Jsoup.parse(debitAmount);
				Float debitAmountFloat = Float.parseFloat(inputText.text());
				Authentication auth = SecurityContextHolder.getContext()
						.getAuthentication();
					if (!(auth instanceof AnonymousAuthenticationToken)) {
						UserDetails userDetail = (UserDetails) auth.getPrincipal();
	
						String usernameLoggedIn = userDetail.getUsername();
						String message = custService.debitAmountForCustomer(
						usernameLoggedIn, debitAmountFloat);
						modelAndView.addObject("debitMessage", message);
						modelAndView.setViewName("debitAmount");
					}
					else {
					modelAndView.setViewName("permission-denied");
					}
			} 
		}
		else {
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
			int counter = 0;
			for (char ch: creditAmount.toCharArray()) {
				if (Character.isDigit(ch) == false) {
					counter ++;
				}
			}
			if (counter>0) {
				modelAndView.addObject("errorMsg", "Please enter the correct amount!");
				modelAndView.setViewName("creditAmount");
				return modelAndView;
			}
			else {
			// Removing the html tags (if present) using JSoup library
				Document inputText = Jsoup.parse(creditAmount);
				Float creditAmountFloat = Float.parseFloat(inputText.text());
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

		if (!(modInfo.getFirstname().isEmpty()
				|| modInfo.getLastname().isEmpty()
				|| modInfo.getPhonenumber().isEmpty()
				|| modInfo.getAddress().isEmpty()
				|| modInfo.getSelection().isEmpty()
				|| modInfo.getSex().isEmpty() || modInfo.getEmail().isEmpty())) {
			// check if user is login
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				String requestedForUsername = userDetail.getUsername();

				// Using Jsoup to parse html (if present in input)
				Document inputFirstname = Jsoup.parse(modInfo.getFirstname());
				Document inputSex = Jsoup.parse(modInfo.getSex());
				Document inputSelection = Jsoup.parse(modInfo.getSelection());
				Document inputLastname = Jsoup.parse(modInfo.getLastname());
				Document inputPhoneNumber = Jsoup.parse(modInfo.getPhonenumber());	
				Document inputEmail = Jsoup.parse(modInfo.getEmail());
				Document inputAddress = Jsoup.parse(modInfo.getAddress());
				
				String fn = inputFirstname.text();
				String ln = inputLastname.text();
				String phNum = inputPhoneNumber.text();
				String address = inputAddress.text();
				int count = 0;
				for (char c: phNum.toCharArray()) {
					if (Character.isDigit(c)) {
						count ++;
					}
				}
				
				Pattern p = Pattern.compile("[!@#${},%^&*+_.-]");
				Matcher match = p.matcher(fn.subSequence(0, fn.length()));
				Matcher match1 = p.matcher(ln.subSequence(0, ln.length()));
				Matcher match2 = p.matcher(address.subSequence(0, address.length()));
				
				if (fn.length()>10 || match.find() == true) {
					modelAndView.addObject("errorMsg1", "Invalid First Name.");
					modelAndView.setViewName("modifyUserExternal");
				}
				else if (ln.length()>10 || match1.find() == true) {
					modelAndView.addObject("errorMsg2", "Invalid Last Name.");
					modelAndView.setViewName("modifyUserExternal");
				}
				
				else if (phNum.length() != 10 && count != 10) {
					modelAndView.addObject("errorMsg3", "Invalid phone number.");
					modelAndView.setViewName("modifyUserExternal");
				}
				else if (address.length()>50 || match2.find() == true) {
					modelAndView.addObject("errorMsg4", "Invalid address.");
					modelAndView.setViewName("modifyUserExternal");
				}
				else {
				
					modInfo.setSex(inputSex.text());
					modInfo.setFirstname(inputFirstname.text());
					modInfo.setLastname(inputLastname.text());
					modInfo.setSelection(inputSelection.text());
					modInfo.setPhonenumber(inputPhoneNumber.text());
					modInfo.setEmail(inputEmail.text());
					modInfo.setAddress(inputAddress.text());
	
					String requestSubmitMessage = custService.modificationRequest(
							requestedForUsername, modInfo);
					modelAndView.addObject("requestSubmitMessage",
							requestSubmitMessage);
	
					modelAndView.setViewName("modifyUserExternal");
				}
			}
			else {
					modelAndView.setViewName("permission-denied");
				}
		} else {
			modelAndView
					.addObject(
							"requestSubmitMessage",
							"Do not leave any of the fields empty. If you do not want to change the value, please input previous value.");
			modelAndView.setViewName("modifyUserExternal");
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

			// Using Jsoup to strip html tags (if present)
			Document deleteAccountOption = Jsoup.parse(deleteAccountYesOrNo);

			// check if user is login
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();

				String requestedForUsername = userDetail.getUsername();
				if (deleteAccountOption.text().equalsIgnoreCase("Yes")) {
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

	// Activate Account from email Address.
	@RequestMapping(value = "/activateAccount/activateAccount", method = RequestMethod.POST)
	public ModelAndView activateRequest(
			@RequestParam("username") String usernameSearch) {
		ModelAndView modelAndView = new ModelAndView();

		Document userInput = Jsoup.parse(usernameSearch);
		String userName = userInput.text();

		if (userName.length() == 0 || userName.length() > 15) {
			modelAndView.addObject("status", "Please Do not modify the url!");
			modelAndView.setViewName("activateAccount");
		} else {
			boolean status = custService.activateAccount(userName);
			if (!status) {
				modelAndView.addObject("status",
						"Your Account is already Active!!!");
				modelAndView.setViewName("activateAccount");
			} else {
				modelAndView.addObject("status",
						"Your Account is Activated, Please login to Access");
				modelAndView.setViewName("activateAccount");
			}
		}
		return modelAndView;
	}

	// Activate Account from email Address.
	@RequestMapping(value = "/activateAccount/{account}", method = RequestMethod.GET)
	public ModelAndView activate(@PathVariable String account, ModelMap model) {
		ModelAndView modelAndView = new ModelAndView();
		model.addAttribute("account", account);
		modelAndView.setViewName("activateAccount");
		return modelAndView;
	}
	
	// Delete transaction
	@RequestMapping(value="/deleteTransaction", method=RequestMethod.POST)
	public ModelAndView deleteSuccessPage(@RequestParam("deleteTransactionID") String deleteTxID) {
		ModelAndView modelAndView = new ModelAndView();
		
		if (!deleteTxID.isEmpty()) {
			// Strip HTML to prevent XSS
			Document doc = Jsoup.parse(deleteTxID);
			String txID = doc.text();
			
			Integer txIDInInt = Integer.parseInt(txID);
			
			// Call the tx delete method
			if (custService.deleteTx(txIDInInt)) {
				modelAndView.addObject("deleteInformation", "Deleted transaction.");
			} else {
				modelAndView.addObject("deleteInformation", "Deletion unsuccessful. Please contact the admin or employee");
			}
			
		} else {
			modelAndView.addObject("emptyBox", "You cannot leave the text-box empty");
		}
		modelAndView.setViewName("transactionsReview");
		return modelAndView;
	}
}
