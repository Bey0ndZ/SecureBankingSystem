package edu.softwaresecurity.group5.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import edu.softwaresecurity.group5.dto.BillPayDTO;
import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.dto.TicketInformationDTO;
import edu.softwaresecurity.group5.dto.UserTransactionsDTO;
import edu.softwaresecurity.group5.model.AddUserInformation;
import edu.softwaresecurity.group5.model.ChangePassword;
import edu.softwaresecurity.group5.model.CustomerInformation;
import edu.softwaresecurity.group5.model.ModifyUserInformation;

public interface CustomerService {

	public String insertCustomerInformation(CustomerInformation custInfo) throws NoSuchAlgorithmException;

	public String insertAddUserInformation(AddUserInformation addInfo) throws NoSuchAlgorithmException;

	public List<CustomerInformationDTO> fetchUserDetails(String usernameSearch);
	public List<CustomerInformationDTO> getUserList();
	public String changeAccountPassword(ChangePassword cust);
	public String unlockAccount(CustomerInformationDTO cust);
	public boolean processBillPay(String loggedInUser, String accountNumber,
			String amountToBeTransferred);
	public List<BillPayDTO> returnBillPaymentDetails(String username);
	public String debitAmountForCustomer(String username, float debitAmount);
	public String creditAmountForCustomer(String usernameLoggedIn,
			Float creditAmountFloat);
	public String modificationRequest(String username, ModifyUserInformation modInfo);
	public String deleteAccount(String username, boolean deleteAccount);
	public String genOtp(String email);
	public boolean activateAccount(String username);
	public boolean deleteAccountBYInternal(String username);
	public List<TicketInformationDTO> getTicketList();
	public CustomerInformationDTO getUserFromAccount(String accountnumber);  
	public String updateAccount(CustomerInformationDTO cust); 
	public boolean transfer(String loggedInUser, String accountNumber,String amountToBeTransferred);
	public boolean pendingTransfer(String loggedInUser, String accountNumber,String amountToBeTransferred);
	public List<UserTransactionsDTO> getUserTransactions(String username);
	public boolean deleteTx(int txID);
}

