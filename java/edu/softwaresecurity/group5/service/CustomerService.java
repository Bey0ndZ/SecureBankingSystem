package edu.softwaresecurity.group5.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import edu.softwaresecurity.group5.dto.BillPayDTO;
import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.model.AddUserInformation;
import edu.softwaresecurity.group5.model.ChangePassword;
import edu.softwaresecurity.group5.model.CustomerInformation;

public interface CustomerService {

	public String insertCustomerInformation(CustomerInformation custInfo) throws NoSuchAlgorithmException;

	public String insertAddUserInformation(AddUserInformation addInfo) throws NoSuchAlgorithmException;

	public List<CustomerInformationDTO> fetchUserDetails(String usernameSearch);
	public List<CustomerInformationDTO> getUserList();
	public CustomerInformationDTO getUserFromAccount(String accountNumber);  
	public String updateAccount(CustomerInformationDTO cust);  
	public String changeAccountPassword(ChangePassword cust);
	public String unlockAccount(CustomerInformationDTO cust);
	public boolean processBillPay(String loggedInUser, String accountNumber,
			String amountToBeTransferred);
	public List<BillPayDTO> returnBillPaymentDetails(String username);
	public String debitAmountForCustomer(String username, float debitAmount);

	public String creditAmountForCustomer(String usernameLoggedIn,
			Float creditAmountFloat);
}

