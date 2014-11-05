package edu.softwaresecurity.group5.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.softwaresecurity.group5.dao.CustomerDAO;
import edu.softwaresecurity.group5.dto.BillPayDTO;
import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.dto.EmployeeInformationDTO;
import edu.softwaresecurity.group5.dto.TicketInformationDTO;
import edu.softwaresecurity.group5.model.AddUserInformation;
import edu.softwaresecurity.group5.model.ChangePassword;
import edu.softwaresecurity.group5.model.CustomerInformation;
import edu.softwaresecurity.group5.model.ModifyUserInformation;

public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDAO custDAO;
	
	public String insertCustomerInformation(CustomerInformation custInfo) throws NoSuchAlgorithmException {
		return custDAO.registerCustomer(custInfo);
	}
	
	public String insertAddUserInformation(AddUserInformation addInfo) throws NoSuchAlgorithmException {
		return custDAO.addUser(addInfo);
	}
	
	public List<CustomerInformationDTO> fetchUserDetails(String usernameSearch) {
		return custDAO.retrieveUserDetails(usernameSearch);
	}
	public List<EmployeeInformationDTO> getUserList() {  
	  return custDAO.getUserList();  
	 }  
	public CustomerInformationDTO getUserFromAccount(String accountNumber){
		return custDAO.getUserFromAccount(accountNumber);
	}

	public String updateAccount(CustomerInformationDTO cust) {
		// TODO Auto-generated method stub
		return custDAO.updateAccount(cust);
	}
	
	public String changeAccountPassword(ChangePassword cust) {
		// TODO Auto-generated method stub
		return custDAO.changeAccountPassword(cust);
	}
	public String unlockAccount(CustomerInformationDTO cust) {
		// TODO Auto-generated method stub
		return custDAO.unlockAccount(cust);
	}

	public boolean processBillPay(String loggedInUser, String accountNumber,
			String amountToBeTransferred) {
		return custDAO.billPayment(loggedInUser, accountNumber, amountToBeTransferred);
	}

	public List<BillPayDTO> returnBillPaymentDetails(String username) {
		return custDAO.getBillPayRequestForCustomer(username);
	}

	public String debitAmountForCustomer(String username, float debitAmount) {
		return custDAO.debitFromUserAccount(username, debitAmount);
	}

	public String creditAmountForCustomer(String usernameLoggedIn,
			Float creditAmountFloat) {
		return custDAO.creditToUserAccount(usernameLoggedIn, creditAmountFloat);
	}

	// Modify user details requests
	public String modificationRequest(String username, ModifyUserInformation modInfo) {
		return custDAO.modifyUserInformationRequest(username, modInfo);
	}

	public String deleteAccount(String username, boolean deleteAccount) {
		return custDAO.removeAccountRequest(username, deleteAccount);
	}
	public String genOtp(String email) {
		return custDAO.generateOTP(email);
	}
	public boolean activateAccount(String username){
		return custDAO.activateAccountRequest(username);
	}
	public boolean deleteAccountBYInternal(String username){
		return custDAO.deleteAccountRequest(username);
	}

	public List<TicketInformationDTO> getPendingTicketList() {
		// TODO Auto-generated method stub
		return custDAO.getPendingTicketList(); 
	}
	public boolean transfer(String loggedInUser, String accountNumber,String amountToBeTransferred) {
		return custDAO.processtransfer(loggedInUser,accountNumber,amountToBeTransferred);
	}
	public boolean pendingTransfer(String loggedInUser, String accountNumber,String amountToBeTransferred) {
		return custDAO.updatePending(loggedInUser,accountNumber,amountToBeTransferred);
	}

	public List<TicketInformationDTO> getApprovedTicketList() {
		// TODO Auto-generated method stub
		return custDAO.getApprovedTicketList();
	}

	public List<TicketInformationDTO> getRejectedTicketList() {
		// TODO Auto-generated method stub
		return custDAO.getRejectedTicketList();
	}
}
